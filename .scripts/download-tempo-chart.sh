helm repo add grafana https://grafana.github.io/helm-charts
helm pull grafana/tempo --untar -d helm-charts
helm template tempo ./helm-charts/tempo > generated-helm-charts-yml/tempo-generated.yml
helm show values grafana/tempo > helm-chart-values/tempo-values.yml
helm install tempo helm-charts/tempo -f helm-chart-values/tempo-override-values.yml
