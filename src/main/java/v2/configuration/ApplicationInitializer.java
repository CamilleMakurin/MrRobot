package v2.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import v2.core.ui.ApplicationGUI;

/**
 * Application startup initialization.
 * Executes registrations after beans are created.
 *
 */
@Component
public class ApplicationInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        ApplicationGUI bean = applicationContext.getBean(ApplicationGUI.class);
        bean.run(false);

    }
}
