package rs.ac.metropolitan.project.di;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApplicationContextTest {

    @Test
    void testGetInstance() {
        var context = new ApplicationContext(new TestInjectionModule());
        var instance = context.getInstance(TestClass.class);
        assertNotNull(instance);
        assertEquals("Test", instance.getValue());
    }

    private static class TestInjectionModule implements InjectionModule {
        @Override
        public Map<Class<?>, Object> getMappings() {
            Map<Class<?>, Object> mappings = new HashMap<>();
            mappings.put(String.class, "Test");
            return mappings;
        }
    }

    private static class TestClass {
        private final String value;

        public TestClass(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}