# Loki Configuration Explanation

This is a Logback configuration file written in XML format. It is used to configure logging for a Spring Boot
application and send log entries to Loki, which is a log aggregation and query system, typically used with Grafana for
visualization. Let's break down each attribute and use case in this configuration file:

1. `<?xml version="1.0" encoding="UTF-8"?>`:
    - This is the XML declaration that specifies the version and character encoding used in the file.

2. `<configuration>`:
    - This is the root element of the XML configuration file, and it contains all the logback configuration.

3. `<include resource="org/springframework/boot/logging/logback/base.xml"/>`:
    - This line includes the base Logback configuration provided by Spring Boot. It's a common practice to include this
      base configuration to get sensible defaults for logging.

4. `<springProperty>`:
    - This element is used to define a Spring property. In this case, it sets the `appName` property to the value
      of `spring.application.name`, which is typically configured in the Spring Boot `application.properties`
      or `application.yml` file. This allows the application name to be dynamically included in log entries.

5. `<appender>`:
    - This element configures a Logback appender named "LOKI" of type `com.github.loki4j.logback.Loki4jAppender`. This
      appender is responsible for sending log entries to Loki.

6. `<http>`:
    - Within the "LOKI" appender, the `<http>` element configures the HTTP endpoint where log entries will be sent. In
      this case, it specifies that logs will be sent to `http://localhost:3100/loki/api/v1/push`. You should replace
      this URL with the actual Loki push API URL for your environment.

7. `<format>`:
    - The `<format>` element defines the format of log entries sent to Loki. It includes the following sub-elements:
        - `<label>`: This sub-element defines labels that will be added to log entries. Labels are key-value pairs that
          help identify log entries. It uses patterns to include information like the `appName`, `HOSTNAME`, and log
          level in the labels.
        - `<message>`: This sub-element specifies the pattern for the log message itself. It uses
          the `${FILE_LOG_PATTERN}` variable, which is expected to be defined elsewhere in the configuration.

8. `<root>`:
    - The `<root>` element configures the root logger, which is the top-level logger that catches all log entries. In
      this case, it sets the log level to "INFO" for the root logger.

9. `<appender-ref ref="LOKI"/>`:
    - Inside the `<root>` element, this line specifies that log entries at the "INFO" level and above should be sent to
      the "LOKI" appender, which was defined earlier.

Overall, this configuration file sets up Logback to send log entries to Loki with additional metadata such as labels for
application name and host name. It also includes the Spring Boot base configuration for logging. You may need to
customize the Loki push endpoint URL and log message format according to your specific environment and requirements.


