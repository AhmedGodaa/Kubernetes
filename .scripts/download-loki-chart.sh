helm repo add grafana https://grafana.github.io/helm-charts
helm pull grafana/loki --untar -d helm-charts
helm template loki ./helm-charts/loki > generated-helm-charts-yml/loki-generated.yml
helm show values grafana/loki > helm-chart-values/loki-values.yml
helm install loki helm-charts/loki -f helm-chart-values/loki-override-values.yml
