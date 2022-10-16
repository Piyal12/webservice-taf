package com.im.service.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotation {
		
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface TestRun {
		public ServiceGroup serviceGroup() default ServiceGroup.UNASSIGNED;
		public String serviceName() default "[unassigned]"; 
		public DataType dataType() default DataType.UNASSIGNED;
		public DataConstraint dataConstraint() default DataConstraint.UNASSIGNED;
		public String testName() default "[unassigned]";
		public String testGroup() default "[unassigned]";
		public String attributeName() default "[unassigned]";
		public String[] includeAttributeNames() default {};
		public String[] excludeAttributeNames() default {};
		public boolean dependsOnEnvironment() default false;
		public int randomStringLength() default 5;
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface TestData {
		public String[] attributeName();
		public String[] value() default {};
	}
}
