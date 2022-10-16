package com.im.service.session;

import com.im.service.common.ServiceGroup;
import com.im.service.common.Environment;

public class SessionProperties {

    private ServiceGroup serviceGroup;
    private Environment environment;
    private String serviceName;
    private String token;

 
    private SessionProperties(Builder builder) {
        this.serviceGroup = builder.serviceGroup;
        this.environment = builder.environment;
        this.serviceName = builder.serviceName;
        this.token = builder.token;

    }

    public static Builder newProperties(final ServiceGroup serviceGroup) {
        return new Builder(serviceGroup);
    }

    public static final class Builder {
        private ServiceGroup serviceGroup;
        private Environment environment;
        private String serviceName;
        private String token;

        public SessionProperties build() {
            return new SessionProperties(this);
        }

        private Builder(final ServiceGroup serviceGroup) {
            this.serviceGroup = serviceGroup;
        }
        
        public Builder environment(final Environment environment) {
            this.environment = environment;
            return this;
        }

        public Builder serviceName(final String serviceName) {
            this.serviceName = serviceName;
            return this;
        }
        
        public Builder token(final String token) {
            this.token = token;
            return this;
        }

    }

    public ServiceGroup getServiceGroup() {
        return serviceGroup;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getServiceName() {
        return serviceName;
    }
    
    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Properties{" + "application=" + serviceGroup + ", environment=" + environment + ", serviceName=" + serviceName + ", token="+ token+  "}";
    }

}
