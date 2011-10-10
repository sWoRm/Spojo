/**
 *
 */
package org.sands.spojo.factories.impl;

import java.io.IOException;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.hibernate.MappingException;
import org.hibernate.cfg.AnnotationConfiguration;
import org.sands.spojo.annotations.Rule;
import org.sands.spojo.annotations.Rules;
import org.sands.spojo.config.SpojoConfiguration;
import org.sands.spojo.exceptions.RuleException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.util.ClassUtils;

/**
 * @author Vincent Palau
 * @Since Feb 27, 2011
 * 
 */
public class CustomAnnotationSessionFactoryBean extends AnnotationSessionFactoryBean {

	private static final String RESOURCE_PATTERN = "/**/*.class";

	private final TypeFilter[] entityTypeFilters = new TypeFilter[] {
	/* ++++++++ */new AnnotationTypeFilter(Entity.class, false),
	/* ++++++++ */new AnnotationTypeFilter(Embeddable.class, false),
	/* ++++++++ */new AnnotationTypeFilter(MappedSuperclass.class, false),
	/* ++++++++ */new AnnotationTypeFilter(org.hibernate.annotations.Entity.class, false) };

	private String[] packagesToScan = null;

	private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private final TypeFilter[] lightweightFilters = new TypeFilter[] {
	/* ++++++++ */new AnnotationTypeFilter(Rules.class, false),
	/* ++++++++ */new AnnotationTypeFilter(Rule.class, false) };

	// TODO[vpalau]: replace @Resource with another strategy
	private SpojoConfiguration spojoConfiguration = null;

	/**
	 * 
	 */
	public CustomAnnotationSessionFactoryBean() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean#scanPackages(org.hibernate.cfg.
	 *      AnnotationConfiguration)
	 */
	@Override
	protected void scanPackages(final AnnotationConfiguration config) {
		if (packagesToScan != null) {

			SpojoConfiguration spojoConfiguration = getLightweightConfiguration();

			try {
				for (String pkg : packagesToScan) {
					String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
							+ ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
					Resource[] resources = resourcePatternResolver.getResources(pattern);
					MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
					for (Resource resource : resources) {
						if (resource.isReadable()) {
							MetadataReader reader = readerFactory.getMetadataReader(resource);
							String className = reader.getClassMetadata().getClassName();
							if (matchesFilter(reader, readerFactory)) {
								config.addAnnotatedClass(resourcePatternResolver.getClassLoader().loadClass(className));
							}
							if (matchesLightweightFilter(reader, readerFactory)) {
								spojoConfiguration.addClass(resourcePatternResolver.getClassLoader().loadClass(className));
							}
						}
					}
				}
			} catch (IOException ex) {
				throw new MappingException("Failed to scan classpath for unlisted classes", ex);
			} catch (ClassNotFoundException ex) {
				throw new MappingException("Failed to load annotated classes from classpath", ex);
			}
		}
	}

	/**
	 * Check whether any of the configured entity type filters matches the current class descriptor contained in the metadata
	 * reader.
	 */
	private boolean matchesFilter(final MetadataReader reader, final MetadataReaderFactory readerFactory) throws IOException {
		return matchesBasicFilter(reader, readerFactory, entityTypeFilters);
	}

	/**
	 * Check whether any of the configured lightweight entity type filters matches the current class descriptor contained in the
	 * metadata reader.
	 */
	private boolean matchesLightweightFilter(final MetadataReader reader, final MetadataReaderFactory readerFactory)
			throws IOException {
		return matchesBasicFilter(reader, readerFactory, lightweightFilters);
	}

	/**
	 * Check whether any of the configured entity type filters matches the current class descriptor contained in the metadata
	 * reader.
	 */
	private boolean matchesBasicFilter(final MetadataReader reader, final MetadataReaderFactory readerFactory,
			final TypeFilter[] typeFilter) throws IOException {
		if (typeFilter != null) {
			for (TypeFilter filter : typeFilter) {
				if (filter.match(reader, readerFactory)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param packagesToScan
	 *            the packagesToScan to set
	 */
	@Override
	public void setPackagesToScan(final String[] packagesToScan) {
		this.packagesToScan = packagesToScan;
	}

	/**
	 * @return the spojoConfiguration
	 */
	protected SpojoConfiguration getLightweightConfiguration() {
		if (spojoConfiguration == null) {
			throw new RuleException("Configuration not initialized yet");
		}
		return spojoConfiguration;
	}

	/**
	 * @param spojoConfiguration
	 *            the spojoConfiguration to set
	 */
	public void setLightweightConfiguration(final SpojoConfiguration spojoConfiguration) {
		this.spojoConfiguration = spojoConfiguration;
	}
}
