helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo update
helm pull prometheus-community/kube-prometheus-stack --untar -d helm-charts
helm template prometheus ./helm-charts/kube-prometheus-stack > generated-helm-charts-yml/prometheus-generated.yml
helm show values prometheus-community/kube-prometheus-stack > helm-chart-values/prometheus-override-values.yml
