package ro.ubb.catalog.web.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ro.ubb.catalog.core.config.JPAConfig;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.core.model.validators.BookValidator;
import ro.ubb.catalog.core.model.validators.ClientValidator;
import ro.ubb.catalog.core.model.validators.PurchaseValidator;


@Configuration
@ComponentScan({"ro.ubb.catalog.core"})
@Import({JPAConfig.class})
@PropertySources({@PropertySource(value = "classpath:local/db.properties"),
})
public class AppLocalConfig {

    /**
     * Enables placeholders usage with SpEL expressions.
     *
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
