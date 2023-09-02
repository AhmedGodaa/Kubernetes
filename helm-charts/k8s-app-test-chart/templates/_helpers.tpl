{{- define "test-template" }}
metadata:
  name: {{ .Values.appName  }}
{{-  end }}

{{- define "test2-template" }}
labels:
  app: {{ .Values.appName  }}
  env: {{ .Values.environment  }}
  version: {{ .Values.image.tag  }}
{{-  end }}