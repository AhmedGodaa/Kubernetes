# Documentation

- The `kotlin` backend Spring Boot app directory is [k8s-test](k8s-test).
- The app contains only one api `/test` which return hello world message.

```text
Hello, World!
```

## Helm

```text
Helm is a tool for managing Kubernetes charts. Charts are packages of pre-configured Kubernetes resources.
Helm is a tool that streamlines installing and managing Kubernetes applications.
```

- Get all helm releases

```shell
helm list
```

- Install Mysql using helm

```shell
helm install my-sql oci://registry-1.docker.io/bitnamicharts/mysql
```

- Validate mysql

```shell
kubectl  kubectl get pods -w --namespace default
```

```text
> Note:

Execute the following to get the administrator credentials:

echo Username: root
MYSQL_ROOT_PASSWORD=$(kubectl get secret --namespace default my-sql-mysql -o jsonpath="{.data.mysql-root-password}" | base64 -d)

To connect to your database:

1. Run a pod that you can use as a client:

   kubectl run my-sql-mysql-client --rm --tty -i --restart='Never' --image  docker.io/bitnami/mysql:8.0.34-debian-11-r31 --namespace default --env MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD --command -- bash

2. To connect to primary service (read/write):

   mysql -h my-sql-mysql.default.svc.cluster.local -uroot -p"$MYSQL_ROOT_PASSWORD"

```

- Get the mysql release secret as json

```shell
kubectl get secret --namespace default my-sql-mysql -o json
```

```json
{
  "apiVersion": "v1",
  "data": {
    "mysql-password": "MWFVRkk3UTFPdw==",
    "mysql-root-password": "V0VIb3l4Vjc5WQ=="
  },
  "kind": "Secret",
  "metadata": {
    "annotations": {
      "meta.helm.sh/release-name": "my-sql",
      "meta.helm.sh/release-namespace": "default"
    },
    "creationTimestamp": "2023-08-31T15:15:09Z",
    "labels": {
      "app.kubernetes.io/instance": "my-sql",
      "app.kubernetes.io/managed-by": "Helm",
      "app.kubernetes.io/name": "mysql",
      "helm.sh/chart": "mysql-9.12.1"
    },
    "name": "my-sql-mysql",
    "namespace": "default",
    "resourceVersion": "109476",
    "uid": "cba38e54-c79f-40d4-b61e-ead7afc27bd0"
  },
  "type": "Opaque"
}
```

- Get the mysql release secret as yaml

```shell
kubectl get secret --namespace default my-sql-mysql -o yaml
```

```yaml
apiVersion: v1
data:
  mysql-password: MWFVRkk3UTFPdw==
  mysql-root-password: V0VIb3l4Vjc5WQ==
kind: Secret
metadata:
  annotations:
    meta.helm.sh/release-name: my-sql
    meta.helm.sh/release-namespace: default
  creationTimestamp: "2023-08-31T15:15:09Z"
  labels:
    app.kubernetes.io/instance: my-sql
    app.kubernetes.io/managed-by: Helm
    app.kubernetes.io/name: mysql
    helm.sh/chart: mysql-9.12.1
  name: my-sql-mysql
  namespace: default
  resourceVersion: "109476"
  uid: cba38e54-c79f-40d4-b61e-ead7afc27bd0
type: Opaque
```

- Get the mysql password from the path direct

```shell
kubectl get secret --namespace default my-sql-mysql -o jsonpath="{.data.mysql-root-password}"
```

- Install Prometheus using helm

1. Create kind cluster because it didn't work on minikube

```shell
kind creawte cluster --name helm-test-cluster
# change the context to the new cluster
kubectl config use-context helm-test-cluster
```

- Install prometheus using helm

```shell
2. Install prometheus using helm


```shell
# Add prometheus helm repo
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
# Validate repo added 
helm repo  list
# Update helm repo
helm repo update
#Install Helm Chart
helm install prometheus prometheus-community/kube-prometheus-stack
```

- Get created pods of helm prometheus installation

```shell
kubectl --namespace default get pods -l "release=prometheus"
#OR to see all created pods 
kubectl get pods -o wide 
# Node the grafana pods
```

- Validate the prometheus service so to access prometheus

```shell
kubectl get services
```

- Will access the prometheus using the grafana service

```shell
kubectl port-forward --namespace default service/prometheus-grafana 3000:80
```

- Access prometheus using the browser

```shell
http://localhost:3000
```

- Temp way of changing the service type

> Edit the type from **ClusterIP** to **NodePort**

```shell
kubectl edit service prometheus-grafana 
```

- Access the chart configuration

> `📝` This can be applied to any helm chart

```shell
helm show values prometheus-community/kube-prometheus-stack
```

- Direct the output to a file

```shell
helm show values prometheus-community/kube-prometheus-stack > prometheus-values.yml
```

- We are able to find the password on the yml for grafana

```yaml
  adminPassword: prom-operator
```

- OR we can use this command with linux to get the password

```shell
helm show values prometheus-community/kube-prometheus-stack | grep adminPassword
```

- Override the values

```shell
helm upgrade prometheus prometheus-community/kube-prometheus-stack --values helm-chart-values/prometheus-values.yml
```

- Override the values - admin password using cmd

```shell
helm upgrade prometheus prometheus-community/kube-prometheus-stack --set grafana.adminPassword=test.Password123
```

> Login with new password to grafana.
> Make Sure to port-forward the service after again apply for new services and pods.

- Change the Cluster Service Type

> Check Documentation [Search GitHub](https://github.com/grafana/helm-charts/tree/main/charts/grafana)

```shell
kubectl upgrade prometheus prometheus-community/kube-prometheus-stack --set grafana.service.type=NodePort
```

- OR - using the yml

```yaml
  service:
    portName: http-web
    type: NodePort
```

- Use override separate value file

```shell
helm upgrade prometheus prometheus-community/kube-prometheus-stack --values helm-chart-values/prometheus-override-values-test1.yml
```

### Avoid SnowFlack Server

> #### **Note📝**
> If we want to create a new cluster in different server of the current server we need to return back to the server
> history and see what changes we have done on this cluster on the past and this is impossible so once we need to create
> new cluster we need to have script all the configurations steps are defined so once we want to create the new cluster
> we run the script and this will replicate the cluster.

- Uninstall current chart

```shell
helm uninstall prometheus
```

- Pull remote helm chart

```shell
# It will be "helm pull + github.url "
# OR 
# "Helm pull + added repo artifactory"
# This downloaded tar file 
helm pull prometheus-community/kube-prometheus-stack
# This will untar the file 
helm pull prometheus-community/kube-prometheus-stack --untar 
```

- Helm install from local chart

```shell
helm install prometheus ./helm-charts/kube-prometheus-stack
```

- Validate the chart installed

```shell
helm list
kubectl get pods 
kubectl get services  
```

> `📝` Note:
> Don't change in the values file instead make override file that override the values of the chart
> so whenever you want to upgrade the pulled chart you don't need to edit one by one the updated value before.
> like file  `helm-chart-values/prometheus-override-values-test1.yml`

- Generate the yaml file from the helm chart

```shell
mkdir helm-charts
cd helm-charts
helm pull prometheus-community/kube-prometheus-stack --untar 
cd ../
helm template prometheus ./helm-charts/kube-prometheus-stack > generated-helm-charts-yml/prometheus-generated.yml
```

> `📝` Note:
>
> Now we can use this yaml to create the application using this yaml there is no need to depend on the chart anymore.
> We can make the yaml as the source of truth for the application and this is the file we are going to maintain.
> This yaml should be stored on source-control, and it should be our own chart reference.

- Install the generated yaml.

```shell
kubectl create -f generated-helm-charts-yml/prometheus-generated.yml
```

- Create Own chart.

> `📝` Note:
>
> The yaml in the templates folder will be an input of golang text processor.
>
> Docs: https://pkg.go.dev/text/template

```shell
helm create helm-test-chart
```

- Delete the current yaml the template's folder.

- Add dummy yaml file to template folder

```yaml
# helm-test-chart/templates/test1.yml
test:
  test1: test1
```

```yaml
# helm-test-chart/templates/test2.yml
test:
  test2: test2
```

```shell
cd helm-test-chart
# This Will generate the yaml file from the templates folder and save into helm-test-chart.yaml
helm template helm-test-chart . > templates/helm-test-chart.yaml
```

- Use default yaml values file to place the values

```shell
# Create new chart k8s-app-test-chart
helm create k8s-app-test-chart
# Remove tempaltes files
rm -rf k8s-app-test-chart/templates/*
# Create new templates files - file in the source-control
touch k8s-app-test-chart/templates/deployment.yaml
touch k8s-app-test-chart/templates/service.yaml
```

- Update some values in the values.yaml

```yaml
replicaCount: 1

image:
  repository: ahmedgodaa/k8s-test
  pullPolicy: IfNotPresent
  # Overrides the image tag whose default is the chart appVersion.
  tag: "v1.0.1"
```

- Generate the yaml file from the helm chart

```shell
# The Generated yaml file will be generated in the templates folder
cd k8s-app-test-chart
helm template k8s-app-test-chart . > generated/k8s-app-test-chart.yaml
```

> `📝` **Note**:
>
> This link contains the helm chart template guide - best practise
> https://helm.sh/docs/chart_template_guide/getting_started/

### Pipeline Function.

```gotemplate
# This will get the value from the values file
# If not present "ahmedgodaa" will be default
# After will lower the repository name
image: {{  .Values.image.repository | default "ahmedgodaa" | lower }}:{{ lower .Values.image.tag }}
```

- Use if statement with go templates

```gotemplate
# If .Values.environment
# equal to "development" add -dev
# equal to "staging" add -stg
# equal to "production" add -prod

image: {{  .Values.image.repository | default "ahmedgodaa" | lower }}:{{ lower .Values.image.tag }}{{ if eq .Values. "development"}}-dev{{else if eq .Values.environment "staging" }}-stg{{else if eq .Values.environment "production"}}-prod{{end}}
```

### Yaml Template

1. it should be in the templates folder
2. it should start with `_` underscore so exclude from the generated yaml file
3. it should end with .tpl extension
4. it should start with **define** function and name of template
5. it should end with **end** function .

Template file

```text
{{ define "test-template" }}
metadata:
  name: {{ .Values.environment  }}
{{ end }}
```

Yaml File

```yaml
the `-` after the brackets remove the white space before the line
apiVersion: v1
kind: Pod
  {{- include "test-template" .  }}
```

### Space sensitive

include function can be used with pipelines but template function no.

- use indent function with include function to include the spaces

Yaml file

```gotemplate
# Yaml
apiVersion: v1
kind: Pod
{{- include "test-template" . }}
{{- include "test2-template" . | indent 2}}
```

Templates file

```text
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
```

- Install final helm chart

```shell
# Node: comment the yaml file "/templates/helm-test-chart.yaml"

# 1. generate the yaml - validation
helm template k8s-app-test-chart . > generated/k8s-app-test-chart.yaml
# 2. validate not exist chart
helm list
# 3. uninstall the chart
helm uninstall k8s-app-test-chart
# 4. install the chart
helm install k8s-app-test-chart ./helm-charts/k8s-app-test-chart --set environment=development
# 5. change to development namespace
kubectl config set-context --current --namespace=development
# 5. upgrade any value
helm upgrade k8s-app-test-chart ./helm-charts/k8s-app-test-chart --set any-attribute=any-value
```

> `📝` **Note**:
>
> 1. Each namespace is different cluster, we need to deploy each namespace separately.
> 2. Need login the namespace context deploy on it.

- ---

## ArgoCD

ArgoCD is a declarative, GitOps continuous delivery tool for Kubernetes.\
ArgoCD is the **Agent** between the desired ***Git Repository*** state and live state ****Kubernetes Deployment***.

#### Workflow without ArgoCD and Problems

1. Push the code and the pipeline will be triggered and the pipeline will build the image and push it to the registry.
2. Then the pipeline will deploy the image to the k8s cluster.
3. Jenkins or Actions should update the version the yaml file or helm to deploy the new version. ❌
4. It should install tools in the pipeline like kubectl to communicate with the cluster. ❌
5. It should provide the cluster credentials to the pipeline to communicate with the cluster. ❌
6. It should provide the aws credentials to the pipeline to communicate with the cluster. ❌
7. Once apply the new changes to cluster, it will not be possible to know the status of the execution. ❌

#### Workflow with ArgoCD

1. Push the code and the pipeline will be triggered and the pipeline will build the image and push it to the registry.
2. Deploy ArgoCD to the cluster.
3. Once the changes applied to the git repository, ArgoCD will detect the changes and will deploy the changes to the
   cluster.

> `📝` **Note**:\
> It is best practise to have the source code and the deployment files in separate repositories.\
> Because this files contains sensitive information like configmaps - secrets - certificates - ingress - services.
> ArgoCD support plane yaml, helm, kustomize, etc... .

#### ArgoCD Functions

ArgoCD compares the current state of the cluster with the desired state of the cluster in the git repository.\
That means The git repository files will be **single source of truth** for the cluster.\
ArgoCD can be configured manually and this will send alert to update this into the git repository as well.

#### ArgoCD benefit

1. ArgoCD track the git repository.
2. If we want to return the previous version we just need to revert the commit and ArgoCD will detect the changes and
   will.
3. **Deployments are versioned.**
4. No need to manually revert or delete updates of the cluster like kubectl uninstall or kubectl delete.
5. Overcome of disaster recovery problem.
6. No need to create cluster roles and user resources changes comes from pull request to the branch.
7. Cluster Credentials no need to be outside the cluster anymore.

## ArgoCD Configuration

### ArgoCD CRD `Custom Resource Definition`

- ArgoCD use CRD to extend the k8s API.
- CRD allow to configure k8s using native k8s yaml files.

#### Example

```yaml
apiVersion: argoproj.io/v1alpha1
kind: Application
metadata:
  name: k8s-test
  namespace: argocd
spec:
  project: default
  source:
    repoURL: https://github.com/ahmedgodaa/kubernetes
    targetRevision: HEAD
    path: k8s-test
  destination:
    server: https://kubernetes.default.svc
    namespace: default
    syncPolicy:
      syncOptions:
        - CreateNamespace=true
    automated:
      prune: true
      selfHeal: true
```

## Installation

```shell
kubectl create namespace argocd
kubectl config set-context --current --namespace argocd
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml
kubectl get svc
kubectl port-forward --namespace argocd service/argocd-server 8080:443
```

Username is: `admin` and the password is autogenerated and saved in a secret `argocd-initial-admin-secret`

- Get the secret

```shell
kubectl get secret argocd-initial-admin-secret --namespace argocd -o yaml
echo  "cDZLQzdZUkFITTItTnlqTQ==" | base64 -d
# OR 
kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 --decode && echo
```




## Istio

```text
Istio is an open-source service mesh that provides a key set of functionality across the microservices in a Kubernetes cluster.
Istio provides an easy way to create a network of deployed services with load balancing, service-to-service authentication, monitoring, and more, without requiring any changes in service code.
Istio supports managing traffic flows between microservices, enforcing access policies, and aggregating telemetry data, all without requiring changes to the microservice code.
```

### Istio Components

1. Traffic Management
2. Security
3. Observability
4. Extensibility

### Istio SlideCar Proxy

Handles all the network traffic between microservices.\
Service Mesh will be created by injecting Istio SlideCar Proxy into each pod.\
Microservices can talk to each other using the Istio SlideCar Proxy.\
The Proxy is Service Mesh

### Istio Control Plane

Manages the Istio proxies inside pods.

### Istio CRD `Custom Resource Definition`

Istio can be configured using k8s yaml files.
Istio use Kubernetes CRD `Custom Resource Definition` to extend the k8s API.

#### Types

1. VirtualService
2. DestinationRule

#### VirtualService

Configure the traffic rules to route the traffic to the correct service and correct version.

#### DestinationRule

Configure Traffic Policies for a specific service.
What kind of Load Balancing to use.

> `📝` **Note**:\
> We don't configure the proxies manually.\
> We configure the control-plane.\
> Istio will inject the proxies into the pods automatically.

### Istio Configure Proxies

Istio have internal registry to store the configuration of the proxies.\
When new microservice get deployed it will automatically registered to this registry.\
Istio Automatically detect the services and endpoints of our application.\

### Istio CM CA `Certificate Authority | Management`

Configure the certificates for the microservices to allow secure communication.

### Istio Ingress Gateway

Alternative to k8s ingress controller.\
Run as pod inside the cluster acts as load balancer.\
Will map the traffic to the service using virtual service component.

## MiniKube

Single Node Cluster

- Install Minikube

```install
minikube start --driver=docker
```

- Start Minikube

```shell
minikube start
minikube start --cpus 8 --memory 3801 
```

- Check Running

```shell
minikube status
```

```text
minikube
type: Control Plane
host: Running
kubelet: Running
apiserver: Running
kubeconfig: Configured
```

- Validate minikube is ready

```shell
kubectl get nodes
```

```text
NAME       STATUS   ROLES           AGE    VERSION
minikube   Ready    control-plane   118m   v1.25.3
```

- Check k8s service deployment

```shell
kubectl get svc
```

```text
NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   129m
```

- services that minikube support

```shell
minikube addons list
```

```text
|-----------------------------|----------|--------------|--------------------------------|
|         ADDON NAME          | PROFILE  |    STATUS    |           MAINTAINER           |
|-----------------------------|----------|--------------|--------------------------------|
| ambassador                  | minikube | disabled     | 3rd party (Ambassador)         |
| auto-pause                  | minikube | disabled     | Google                         |
| cloud-spanner               | minikube | disabled     | Google                         |
| csi-hostpath-driver         | minikube | disabled     | Kubernetes                     |
| dashboard                   | minikube | disabled     | Kubernetes                     |
| default-storageclass        | minikube | enabled ✅   | Kubernetes                     |
| efk                         | minikube | disabled     | 3rd party (Elastic)            |
| freshpod                    | minikube | disabled     | Google                         |
| gcp-auth                    | minikube | disabled     | Google                         |
| gvisor                      | minikube | disabled     | Google                         |
| headlamp                    | minikube | disabled     | 3rd party (kinvolk.io)         |
| helm-tiller                 | minikube | disabled     | 3rd party (Helm)               |
| inaccel                     | minikube | disabled     | 3rd party (InAccel             |
|                             |          |              | [info@inaccel.com])            |
| ingress                     | minikube | disabled     | Kubernetes                     |
| ingress-dns                 | minikube | disabled     | Google                         |
| istio                       | minikube | disabled     | 3rd party (Istio)              |
| istio-provisioner           | minikube | disabled     | 3rd party (Istio)              |
| kong                        | minikube | disabled     | 3rd party (Kong HQ)            |
| kubevirt                    | minikube | disabled     | 3rd party (KubeVirt)           |
| logviewer                   | minikube | disabled     | 3rd party (unknown)            |
| metallb                     | minikube | disabled     | 3rd party (MetalLB)            |
| metrics-server              | minikube | disabled     | Kubernetes                     |
| nvidia-driver-installer     | minikube | disabled     | Google                         |
| nvidia-gpu-device-plugin    | minikube | disabled     | 3rd party (Nvidia)             |
| olm                         | minikube | disabled     | 3rd party (Operator Framework) |
| pod-security-policy         | minikube | disabled     | 3rd party (unknown)            |
| portainer                   | minikube | disabled     | 3rd party (Portainer.io)       |
| registry                    | minikube | disabled     | Google                         |
| registry-aliases            | minikube | disabled     | 3rd party (unknown)            |
| registry-creds              | minikube | disabled     | 3rd party (UPMC Enterprises)   |
| storage-provisioner         | minikube | enabled ✅   | Google                         |
| storage-provisioner-gluster | minikube | disabled     | 3rd party (Gluster)            |
| volumesnapshots             | minikube | disabled     | Kubernetes                     |
|-----------------------------|----------|--------------|--------------------------------|
```

- Access minikube services

```shell
minikube dashboard
```

- Stop minikube

```shell
minikube stop
```

- Delete minikube cluster

```shell
minikube delete
```

## Kind

    Multi-Node Cluster

- Download kind

```shell
curl.exe -Lo kind-windows-amd64.exe --ssl-no-revoke https://kind.sigs.k8s.io/dl/v0.20.0/kind-windows-amd64
Move-Item .\kind-windows-amd64 c:\k8s\kind.exe
```

- Create Cluster

```shell 
# This will create single node cluster
kind create cluster --name kind-cluster
```

- Create with configuration

```shell    
# This will create cluster with 4 nodes 2 master-nodes 2 worker-nodes
kind create cluster --name multi-node-cluster --config cluster-config1.yml
```

```shell
# Login to the multi-node cluster
kubectl config use-context multi-node-cluster
```

```shell
# Get the 4 nodes that created in the cluster 
kubectl get nodes
``` 

- Get cluster info

```shell
kubectl cluster-info --context kind-kind-cluster
```

```text
Kubernetes control plane is running at https://127.0.0.1:65458
CoreDNS is running at https://127.0.0.1:65458/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy
```

- Get clusters

```shell
kind get clusters
```

- Delete cluster

```shell
# name of the cluster without kind- prefix
kind delete cluster --name ahmed-cluster
```

## Kube Config File

    kubectl use to authenticate and login to cluster.

- Get clusters

```shell
kubectl config get-contexts
```

```text
CURRENT   NAME             CLUSTER          AUTHINFO         NAMESPACE
          docker-desktop   docker-desktop   docker-desktop
*         kind-kind        kind-kind        kind-kind
          minikube         minikube         minikube         default
```

- Switch between clusters contexts

```shell
kubectl config use-context minikube
```

```shell
# Now any command will run on cluster -  minikube 
kubectl get nodes 
kubectl get pods  
```

- get kube config file directory

```shell
ls ~/.kube/config
```

```text
Directory: C:\Users\ahmedgodaa\.kube


Mode                 LastWriteTime         Length Name
----                 -------------         ------ ----
-a----         8/21/2023   9:24 PM          11926 config

```

## Docker Registry

    Docker registry is a repository to store docker images.

### Public Docker Registry

- docker env required by minikube

```shell
    minikube docker-env
```

      $Env:DOCKER_TLS_VERIFY = "1"
      $Env:DOCKER_HOST = "tcp://127.0.0.1:54613"
      $Env:DOCKER_CERT_PATH = "C:\Users\EGYPT_LAPTOP\.minikube\certs"
      $Env:MINIKUBE_ACTIVE_DOCKERD = "minikube"
      # To point your shell to minikube's docker-daemon, run:
      # & minikube -p minikube docker-env --shell powershell | Invoke-Expression

- Set System env

```shell
$Env:DOCKER_HOST = "tcp://127.0.0.1:54613"
```

- Validate env saved

```shell
 echo $Env:DOCKER_HOST
```

- Rename exist docker image if build on local

```shell
docker tag k8s-test ahmedgodaa/k8s-test:v1.0.1
```

```shell
docker images
```

```shell
docker push ahmedgodaa/k8s-test:v1.0.1
```

- Create **Deployment**

```shell
kubectl apply -f dp-backend-dev.yml
```

- Create **Service**

```shell
kubectl apply -f k8s-test-service.yml
```

- Get **IP**

```text
 Since we expose our service as NodePort
 We can able to access it using node ip and node port.
```

```shell
kubectl get nodes -o wide
```

- Get Nodes IPs - **Minikube**

```text
NAME       STATUS   ROLES           AGE     VERSION   INTERNAL-IP    EXTERNAL-IP   OS-IMAGE             KERNEL-VERSION                      CONTAINER-RUNTIME
minikube   Ready    control-plane   3h37m   v1.25.3   192.168.49.2   <none>        Ubuntu 20.04.5 LTS   5.15.90.4-microsoft-standard-WSL2   docker://20.10.20
```

- OR

```shell
minikube ip
```

- Get Port - **Service Port**

```shell
kubectl get svc
```

```text
k8s-test     NodePort    10.108.198.207   <none>        8080:32485/TCP   8m35s
kubernetes   ClusterIP   10.96.0.1        <none>        443/TCP          3h41m
```

- Access Application - **Linux**

```text
# Access the service using node ip and node port
http://192.168.49.2:32485
```

- On **windows**

```shell
# Service should be exposed by minikube 
minikube service k8s-test
```

```text
|-----------|----------|-------------|---------------------------|
| NAMESPACE |   NAME   | TARGET PORT |            URL            |
|-----------|----------|-------------|---------------------------|
| default   | k8s-test |        8080 | http://192.168.49.2:31089 |
|-----------|----------|-------------|---------------------------|
🏃  Starting tunnel for service k8s-test.
|-----------|----------|-------------|------------------------|
| NAMESPACE |   NAME   | TARGET PORT |          URL           |
|-----------|----------|-------------|------------------------|
| default   | k8s-test |             | http://127.0.0.1:51812 |
|-----------|----------|-------------|------------------------|
🎉  Opening service default/k8s-test in default browser...
❗  Because you are using a Docker driver on windows, the terminal needs to be open to run it.
```

- Access the service - **Windows**

```text
# Access the service using tunnel
http://127.0.0.1:51812
```

### Public Docker Registry

- Start **Docker Registry Image**

```shell
docker run -d -p 5000:5000 --restart=always --name private-registry registry:2
```

- Start **Docker Registry Image Automatically**

```shell
docker run -d  -p 5000:5000 --restart=always --name registry registry:2
```

- get docker registry ip

```shell
docker inspect -f "{{ .NetworkSettings.IPAddress }}" private-registry
```

```text
172.17.0.2
```

- Tag **image**

```shell
docker tag ahmedgodaa/k8s-test:v1.0.1 localhost:5000/k8s-test:v1.0.1
```

- Push **image**

```shell
docker push localhost:5000/k8s-test:v1.0.1
```

```shell
docker pull localhost:5000/k8s-test:v1.0.1
```

- Stop **Docker Registry**

```shell
docker stop private-registry
```

- Minikube **Docker Registry**

```shell
minikube addons enable registry
```

```text
* registry is an addon maintained by Google. For any concerns contact minikube on GitHub.
You can view the list of minikube maintainers at: https://github.com/kubernetes/minikube/blob/master/OWNERS
╭──────────────────────────────────────────────────────────────────────────────────────────────────────╮
│                                                                                                      │
│    Registry addon with docker driver uses port 58201 please use that instead of default port 5000    │
│                                                                                                      │
╰──────────────────────────────────────────────────────────────────────────────────────────────────────╯
* For more information see: https://minikube.sigs.k8s.io/docs/drivers/docker
  - Using image docker.io/registry:2.8.1
  - Using image gcr.io/google_containers/kube-registry-proxy:0.4
* Verifying registry addon...
* The 'registry' addon is enabled

```

#### Access the registry - Manually

```shell
kubectl get namespaces 
```

```text
NAME                   STATUS   AGE
default                Active   18h
kube-node-lease        Active   18h
kube-public            Active   18h
kube-system            Active   18h
kubernetes-dashboard   Active   18h
```

- Get the services on the kube-system virtual cluster namespace

```shell
kubectl get svc --namespace kube-system
```

```text
NAME       TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)                  AGE
kube-dns   ClusterIP   10.96.0.10      <none>        53/UDP,53/TCP,9153/TCP   18h
registry   ClusterIP   10.106.212.23   <none>        80/TCP,443/TCP           6m55s
```

##### Expose the registry service

1. It uses ClusterIP, So we can't directly access it from outside the cluster
2. It can be port-forwarded to localhost:5000

```shell 
kubectl port-forward --namespace kube-system service/registry 5000:80
```

- **Validate** the registry is accessible

```shell
curl http://localhost:5000/v2/_catalog
```

```text
{"repositories":["k8s-test"]}
```

## ECR

```text
Repository to store docker images alternative to dockerhub, Nexus.

Amazon Elastic Container Registry (ECR) is a fully managed container registry that makes it easy to store, manage,
share, and deploy your container images and artifacts anywhere.
```

## ECS

        AWS Service to manage containers
        ECS works as minikube or Kind cluster but on the cloud.
        ECS is a container orchestration service that supports Docker containers and allows you to easily run and scale containerized applications on AWS.
        ECS eliminates the need for you to install, operate, and scale your own cluster management infrastructure.
        ECS is a regional service that simplifies running application containers in a highly available manner across multiple Availability Zones within a Region.
        ECS is a great choice to run containers for several reasons.
        First, it reduces the management overhead of running containers at scale.
        Second, it provides security capabilities, such as IAM roles for tasks, that allow you to control access to your containers.
        Finally, ECS integrates with other AWS services, such as Elastic Load Balancing, Amazon VPC, AWS IAM, and AWS CloudFormation, to provide a complete solution for running a wide range of containerized applications.

### ECS with EC2

```text
Requires Docker installed on EC2
Requires ECS Agent installed on EC2.
```

- **Pros**

```text
full access to infrastructure.
```

- **Cons**

```text
It Requires EC2 instances to run containers on.
It Requires Docker installed on EC2 instances.
Manage the Virtual Machines.
Check enough resources available on EC2 instances.
```

### ECS with Fargate

```text
Fargate is a technology that you can use with Amazon ECS to run containers without having to manage servers or clusters of Amazon EC2 instances.
Fargate is a serverless engine for containers that works with both Amazon Elastic Container Service (ECS) and Amazon Elastic Kubernetes Service (EKS).
Fargate removes the need to provision and manage servers, lets you specify and pay for resources per application.
Fargate check how uch CPU and memory your containerized application needs and it will launch the containers for you.
You don't have to select instance types or manage scaling.
```

- **Pros**

```text
Don't pay for control plane.
Integrate with AWS services.
CloudWatch for monitoring.
Elastic Load Balancing for Loadbalancing.
IAM for security.
VPC for networking.
```

## EKS

```text
EKS make easy to migrate to another cloud provider.
Amazon Elastic Kubernetes Service (Amazon EKS) is a fully managed Kubernetes service.
Kubernetes is open-source software that allows you to deploy and manage containerized applications at scale.
EKS runs Kubernetes control plane instances across multiple Availability Zones to ensure high availability.
EKS automatically detects and replaces unhealthy control plane instances, and it provides automated version upgrades and patching for them.
EKS is also integrated with many AWS services to provide scalability and security for your applications.
```

- **Pros**

```text
Full access to k8s ecosystem like helm.
```

- **Cons**

```text
Pay for the control plane unlike ECS.
Migration from EKS to another cloud provider is not easy.
```

- **How it works**

```text
Eks deploys and manages the control plane "master nodes".
Eks duplicate the control plane across multiple religious.
Eks replicate ETCD cluster across multiple religious. 
Eks deploy worker nodes on virtual EC2 instances called "compute fleet" and connect to EKS.
The worker nodes are EC2 instances that run the Kubernetes kubelet agent.
Can be semi-managed by AWS or fully managed by AWS.
```

### Eks with Fargate

```text
Make fully-managed serverless compute engine for containers.
Using Fargate with Amazon EKS allows you to run Kubernetes pods without having to provision and manage EC2 instances.
```

## Deployment

### Create Deployment

- Create deployment with 3 replicaset

```shell
kubectl create deployment test-deployment --image=ahmedgodaa/k8s-test:v1.0.1
```

- Describe deployment.

This will show the deployment details like: **Replicas** - **Selector** - **Strategy** - **Template** - **Containers**

```shell
kubectl describe deployment test-deployment
```

### Update Deployment

#### Update Deployment Command line

```shell
kubectl apply -f deployment/dp-backend-dev.yml
```

```shell
# Update Deployment
 kubectl set image deployment/k8s-test k8s-test=ahmedgodaa/k8s-test:v1.0.1-dev
```

`k8s-test` in the command, is the name of the container.
pod is creating and container is terminating

```text
$ kubectl get all
NAME                            READY   STATUS              RESTARTS   AGE
pod/k8s-test-7b9dd67d87-mptfp   1/1     Terminating         0          54m
pod/k8s-test-7b9dd67d87-sst8s   1/1     Running             0          54m
pod/k8s-test-fd8fdd5c-8qcxv     1/1     Running             0          31s
pod/k8s-test-fd8fdd5c-tdb9q     0/1     ContainerCreating   0          6s
```

#### Update Deployment Yaml

##### Using live edit

This Will Open text editor once update and close the editor the deployment will be updated.

```shell
kubectl edit deployment k8s-test 
```

```text
deployment.apps/k8s-test edited
```

##### Check the Update Status

```shell
kubectl rollout status deployment k8s-test
```

```text
Waiting for rollout to finish: 2 out of 3 new replicas have been updated...
```

```text
deployment "k8s-test" successfully rolled out
```

#### Rolling Back to a Previous Revision

Get All The History with Versions

```shell
 kubectl rollout history deployment k8s-test
```

Rollback to Version

```shell
kubectl rollout undo deployment k8s-test --to-revision=2
```

```text
deployment.apps/k8s-test rolled back
```

### Scaling a Deployment

Upscale replicas to 5

```shell
kubectl scale deployment k8s-test --replicas=5
```

```text
NAME                          READY   STATUS              RESTARTS   AGE
pod/k8s-test-fd8fdd5c-5vg9k   0/1     ContainerCreating   0          5s
pod/k8s-test-fd8fdd5c-fskjx   0/1     ContainerCreating   0          5s
pod/k8s-test-fd8fdd5c-pbb7d   0/1     ContainerCreating   0          5s
pod/k8s-test-fd8fdd5c-pf56b   1/1     Running             0          5m34s
pod/k8s-test-fd8fdd5c-zckh6   1/1     Running             0          5m39s
```

Downscale replicas to 2

```shell
kubectl scale deployment k8s-test --replicas=2
```

```text
NAME                      READY   STATUS        RESTARTS   AGE
k8s-test-fd8fdd5c-5vg9k   1/1     Terminating   0          10m
k8s-test-fd8fdd5c-fskjx   1/1     Terminating   0          10m
k8s-test-fd8fdd5c-pbb7d   1/1     Terminating   0          10m
k8s-test-fd8fdd5c-pf56b   1/1     Running       0          16m
k8s-test-fd8fdd5c-zckh6   1/1     Running       0          16m
```

### Auto scale

This will create k8s object HorizontalPodScaler  `hpa`

```shell
kubectl autoscale deployment k8s-test --min=2 --max=5 --cpu-percent=90
```

```text
horizontalpodautoscaler.autoscaling/k8s-test autoscaled
```

Get HPAs

```shell
kubectl get hpa
```

Delete HPA

```shell
kubectl delete hpa k8s-test
```

```text
horizontalpodautoscaler.autoscaling "k8s-test" deleted
```

### Pause and Resume a Rollout

Pause the deployment

```shell
kubectl rollout pause deployment k8s-test
```

Resume the deployment

```shell
kubectl rollout resume deployment k8s-test
```

### Deployment Strategies

#### Rolling Update

Default strategy is `RollingUpdate`

`maxSurge` max number of pods that can be created above the desired number of pods.

`maxUnavailable` max number of pods that can be unavailable during the update process.

```yaml
# it will not down all the pods when we rolling update only 30% of the pods `3 pods`
# it will not up all the pods when we rolling update only 30% of the pods `3 pods`
spec:
  replicas: 10
  strategy:
    type: RollingUpdate
    rollingUpdate:
      maxSurge: 30%
      maxUnavailable: 30%
```

#### Example

1. Create deployment
2. Change image from `v1.0.1` to `v1.0.1-dev`
3. Deploy again

```shell
kubectl apply -f dp-backend-test2.yml
```

#### Recreate

the old pods will be all terminated before new pods are created.
all new pods will be created at the same time.
> `📝` **Note**: This will cause downtime.

```yaml
spec:
  replicas: 10
  strategy:
    type: Recreate
```

## Service

The `selector` and `targetPort` should be the same of the pod.\
Service connect the pod using labels and selectors only.\
Name of the pod is not important.

> `📝` **Note**:\
`selector` is mandatory to be able to select the pods\
`targetPort` is mandatory to equal the `containerPort`
`containerPort` is mandatory expose the port inside the container\
`type` is mandatory to expose the service to outside the cluster

### Service Types

1. **ClusterIP** - Pods accessible only on the cluster.
2. **NodePort** - Use node ip and node port to access outside cluster.
3. **LoadBalancer** - Use with cloud providers load balancer controller.
4. **ExternalName** - Use with endpoint object to access outside cluster.
5. **Headless** - Access Pods directly, without service

### External Name

Access Prometheus from outside the cluster

```shell
# Download Prometheus and run container
docker run -p 9090:9090  prometheus prom/prometheus
# Validate prometheus running
curl localhost:9090/metrcis
```

```yaml
apiVersion: v1
kind: Service
metadata:
  name: prometheus-service
spec:
  type: NodePort
  selector:
    app: prometheus
  ports:
    - port: 80
      targetPort: 9090
      nodePort: 30000

```

Create Endpoint for the service

```yaml
# Service Running on ip 10.104.86.59
apiVersion: v1
kind: Endpoints
metadata:
  name: prometheus
subsets:
  - addresses:
      - ip: 10.104.86.59
    ports:
      - port: 9090
        name: http
        protocol: TCP
```

## Ingress

Expose the service to outside the cluster.\
Manage the traffic to the services.\
Provide load balancing, SSL termination and name-based virtual hosting.\

### Ingress Controller

In order for the Ingress resource to work, the cluster must have an ingress controller running.

Unlike other types of controllers which run as part of the kube-controller-manager binary, Ingress controllers are not
started automatically with a cluster. Use this page to choose the ingress controller implementation that best fits your
cluster.

### Install

- Set node labels

```shell
kubectl label node/minikube ingress-ready=true
# Validate
kubectl describe node minikube
```

```shell
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/controller-v1.0.0/deploy/static/provider/cloud/deploy.yaml
```

- OR - Using helm chart

```shell
  helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
  helm repo update
  cd helm-charts
  helm pull ingress-nginx/ingress-nginx --untar
  helm install ingress-nginx ./helm-charts/ingress-nginx  
```

- Generate ingress-nginx yaml file

```shell
# generate the yaml file
helm template ingress-nginx ./helm-charts/ingress-nginx  > ./generated-helm-charts-yml/ingress-nginx.yml
# show the values of the yaml
helm show values ./helm-charts/ingress-nginx > ./generated-helm-charts-yml/ingress-nginx-values.yml
```

#### Validate Ingress Controller Working

```shell
kubectl port-forward service/ingress-nginx-controller 80
curl  127.0.0.1:80
```

```html
<!-- will return not found page now working-->
<!-- it didn't know where to map this request need ingress object-->
<html>
<head><title>404 Not Found</title></head>
<body>
<center><h1>404 Not Found</h1></center>
<hr>
<center>nginx</center>
</body>
</html>
```

### Ingress Object

- Setup deployment and service

```shell
kubectl apply -f ./deployment/dp-backend-dev.yml
kubectl apply -f ./service/svc-backend-dev.yml
```

- Add Ingress Object to the service

```shell
kubectl apply -f ./ingress/ing-backend-test1.yml
```

- Access Ingress Object

```shell
# port-forward ingress controller service
kubectl port-forward service/ingress-nginx-controller 80
# access the app from the ingress
curl http://127.0.0.1/test 
```

```text
Hello, World!
```

### Path Based Virtual Hosting

- This will follow the endpoint of the request
- If `/login` will map to log in service
- If `/category` will map to category service

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: path-based-ingress
  annotations:
    kubernetes.io/ingress.class: nginx
spec:
  defaultBackend:
    service:
      name: k8s-test
      port:
        number: 8080
  rules:
    - http:
        paths:
          - pathType: Prefix
            path: /login
            backend:
              service:
                name: login-service
                port:
                  number: 8080
          - pathType: Prefix
              path: /category
              backend:
                service:
                  name: category-service
                  port:
                    number: 8080
```         

### Name Based Virtual Hosting

- This will follow domain of the request
- If `login.example.com` will map to log in service
- If `catalog.example.com` will map to category service
- If `example.com` will map to default service

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: name-based-ingress
  annotations:
    kubernetes.io/ingress.class: nginx
spec:
  defaultBackend:
    service:
      name: k8s-test
      port:
        number: 8080
  rules:
    - host: login.example.com
      http:
        paths:
          - pathType: Prefix
            path: /
            backend:
              service:
                name: login-service
                port:
                  number: 8080
    - host: catalog.example.com
      http:
        paths:
          - pathType: Prefix
            path: /
            backend:
              service:
                name: catalog-service
                port:
                  number: 8080
```

## Namespaces

- To specify namespace in any k8s service ( **Deployment** - **Service** - **ConfigMap** - **Secrets** )

```yaml
apiVersion: v1
kind: Deployment
metadata:
name: k8s-test
namespace: development

```

- To know which object accept namespace

```shell
kubectl api-resources --namespaced=true
```

    Divide cluster to virtual clusters  FE - BE - DB
    Set Permissions:
        BE only have access to DB cluster.
        Payment Cluster fully isolated.
        Device Cluster By environment. DEV - STAG - PROD
        DEV: full access 
        STG: can ssh pods troubleshoot application
        PROD: Read Access Check if application running
        PROD: Jenkins - Actions - Authorized deployment pipline

    K8s consists of multiple nodes Master - Worker
    WR nodes expose CPU and Memory resources
    Container demand the resources from WR nodes

    K8s check available resources in each node 
    K8s assign new pods based on available resources

    Each deployment can have his own namespace 
    
    Cluster have coast:
        Each cluster: at least have 6 node
            Have at least 3 master node. for high availability.
            Have at least 3 external etcd cluster.
            Have system components need to be deployed: promenades - alert-manager - ingress-controller.
            Upgrade All Clusters - Manage Certificates - Adminstration.
    Needed:
        Maximum nodes in the cluster. 
            Create new cluster. - scale horizontally.

- Create Namespace

```shell
kubectl create namespace test-namespace
```

- Get Namespaces

```shell   
kubectl get namespaces
```

- Get Pods in Namespace

```shell
kubectl get pods -n test-namespace
```

- Switch between namespaces

```shell
kubectl config set-context --current --namespace=test-namespace
```

- Validate current namespace

```shell
kubectl config view --minify
```

```yaml
apiVersion: v1
clusters:
  - cluster:
      certificate-authority: C:\Users\ahmedgodaa\.minikube\ca.crt
      extensions:
        - extension:
            last-update: Fri, 25 Aug 2023 10:01:13 EET
            provider: minikube.sigs.k8s.io
            version: v1.28.0
      namespace: test-namespace
      user: minikube
    name: minikube
current-context: minikube
kind: Config
preferences: { }
users:
  - name: minikube
    user:
      client-certificate: C:\Users\ahmedgodaa\.minikube\profiles\minikube\client.crt
      client-key: C:\Users\ahmedgodaa\.minikube\profiles\minikube\client.key
```

- OR

```shell
kubectl config get-contexts
 ```

```text
CURRENT   NAME             CLUSTER          AUTHINFO         NAMESPACE
          docker-desktop   docker-desktop   docker-desktop
          kind-kind        kind-kind        kind-kind
*         minikube         minikube         minikube         test-namespace
```

- Describe namespace

```shell
kubectl describe namespace test-namespace
```

- Return to default namespace

```shell
kubectl config set-context --current --namespace=default
```

- Delete namespace

```shell
kubectl delete namespace test-namespace
```

- Create namespace with yaml or json file

```shell
kubectl create -f namespace.yml
```

- Create development and production namespaces.

```shell
kubectl create -f https://k8s.io/examples/admin/namespace-dev.json 
kubectl create -f https://k8s.io/examples/admin/namespace-prod.json 
```

- Login into development namespace

```shell
kubectl config set-context --current --namespace=development
```

- Create deployment in development namespace

```shell
kubectl create deployment spring-test --image=ahmedgodaa/k8s-test:v1.0.1 --namespace=development   
```

```text
NAME                          READY   STATUS    RESTARTS   AGE
spring-test-c7ff98855-n797g   1/1     Running   0          11s
```

## Labels and Selectors

```text
Labels are key/value pairs that are attached to objects, such as pods.
Labels are intended to be used to specify identifying attributes of objects that are meaningful and relevant to users, but do not directly imply semantics to the core system.
Labels can be used to organize and to select subsets of objects.
Labels can be attached to objects at creation time and subsequently added and modified at any time.
Each object can have a set of key/value labels defined.
Each Key must be unique for a given object.

Selectors are the core grouping primitive in Kubernetes.
They are used to identify a set of objects that are relevant for a particular user.
A selector can be defined as a set of requirements, and any object that matches those requirements exactly will be selected.
Requirements are separated into two categories: equality-based and set-based.

equality-based requirements allow filtering by label keys and values.
set-based requirements allow filtering based upon the presence or absence of labels.
```

### Label

```yaml
# have 4 labels app - type - version - env
# should match all of them to select the pod
apiVersion: v1
kind: Pod
metadata:
  name: k8s-test
  labels:
    app: k8s-test
    type: backend
    version: v1.0.1
    env: development

```

- Selectors example

```yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: k8s-test
  #  labels: used when i try to access the deployment from the service
  labels:
    app: k8s-test
    type: backend
    env: development
    version: v1.0.1
spec:
  selector:
    matchLabels:
      app: k8s-test
      type: backend
      version: v1.0.1
      env: development
  template:
    spec:
      containers:
        - name: k8s-test
          image: ahmedgodaa/k8s-test:v1.0.1

```

### Example

**will create 2 deployment with different versions**

- Deployment 1

```yaml
metadata:
  name: k8s-test
  labels:
    app: k8s-test
    type: backend
    version: v1.0.1
```

- Deployment 2

```yaml
metadata:
  name: k8s-test
  labels:
    app: k8s-test
    type: backend
    version: v1.0.2
```

- Create service

This service will expose only the pods with version v1.0.1
If version changed to v1.0.2 only service 2 will be exposed.

```yaml
spec:
  selector:
    matchLabels:
    app: k8s-test
    type: backend
    version: v1.0.1
```

### Deployment Selector

- Labels and selector for deployment service explained

> ### ``📝`` **Note**
>
>The .spec.selector field defines how the created ReplicaSet finds which Pods to manage. In this case, you select a
> label that is defined in the Pod template (app: nginx). However, more sophisticated selection rules are possible, as
> long as the Pod template itself satisfies the rule.

> Labels should be the same of the selectors

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: k8s-test
  namespace: development

  #  They provide metadata information about the deployment itself - used to accessed by other resources like services.
  #  Other services within the Kubernetes ecosystem can use these labels to identify and work with the deployment
  #  This will be associated with the deployment object

  labels:
    app: k8s-test
    env: development
    version: v1.0.1
spec:

  # The selector field defines how the Deployment finds which Pods to manage.
  # They help in selecting the pods that are controlled by this deployment.
  # Other Kubernetes components, such as services or ingress controllers, can use these labels to establish connections or routing rules to the pods managed by this deployment.


  selector:
    matchLabels:
      app: k8s-test
      env: development
      version: v1.0.1
  replicas: 2

  # The labels specified here are associated with the pods that will be created based on the template.
  # These labels can be used for various purposes, such as pod selection, service discovery, or monitoring.
  # Other services or components within the Kubernetes ecosystem can use these labels to target or interact with the pods created by this deployment.

  # Pod template  
  template:
    # By keeping the selector labels and template metadata labels the same
    # you ensure that the pods created by the deployment will have labels
    # matching the selector labels. This allows the deployment to manage and
    # control the pods effectively.
    metadata:
      labels:
        app: k8s-test
        env: development
        version: v1.0.1
    spec:
      containers:
        - name: k8s-test
          image: ahmedgodaa/k8s-test:v1.0.1
          imagePullPolicy: IfNotPresent
          ports:
            - containerPort: 8080
```

## Node Selector

nodeSelector is the simplest recommended form of node selection constraint. You can add the nodeSelector field to your
Pod specification and specify the node labels you want the target node to have. Kubernetes only schedules the Pod onto
nodes that have each of the labels you specify.

```

NODE SELECTOR IN POD AND NODE LABLES SHOULD BE THE SAME TO RUN THE POD INTO THAT NODE

```

- **Example 1**

```yaml
# Pod
apiVersion: v1
kind: Pod
metadata:
  name: k8s-test
spec:

  nodeSelector:
    app: monitoring
    version: v1.0.1

  containers:
    - name: k8s-test
      image: ahmedgodaa/k8s-test:v1.0.1
 ```

```yaml
# Node
apiVersion: v1
kind: Node
metadata:
  name: node-name
  # nodeSelector and node labels should are the same.
  labels:
    app: monitoring
    version: v1.0.1

```

**Example 2**

- Use labels to select nodes to run the application on.
    - Create kind cluster with 1 master node and 2 worker nodes
    - Worker nodes have 2 different zones.


- Create Cluster

```shell
# cluster-config2.yml have 1 master node and 2 worker nodes with different zones
kind create cluster --name test-cluster --config kind/cluster-config2.yml
```

- Get Nodes

```shell
kubectl get nodes
```

```text
NAME                               STATUS   ROLES           AGE     VERSION
multi-node-cluster-control-plane   Ready    control-plane   6m22s   v1.27.3
multi-node-cluster-worker          Ready    <none>          5m55s   v1.27.3
multi-node-cluster-worker2         Ready    <none>          5m55s   v1.27.3
```

- Get specific nodes in specific zone

```shell
 kubectl get nodes --selector zone=us-west-b
```

```text
NAME                         STATUS   ROLES    AGE   VERSION
multi-node-cluster-worker2   Ready    <none>   10m   v1.27.3
```

- Create Pod - we will not be able to know which node will run the pod

```shell
kubectl create -f pod/test-pod1.yml
```

- Specify node to run the pod using nodeSelector in the yaml
    - this will create node at all nodes with zone=us-west-b

```shell
# test-pod2.yml have nodeSelector with zone=us-west-b
kubectl create -f pod/test-pod2.yml
```

## Nodes

- Get nodes

```shell
kubectl get nodes
```

- Create Cluster

```shell
kind create cluster --name test-cluster --config kind/cluster-config2.yml
```

- Get docker containers

```shell
# Kind create the node into docker container
docker ps 
```

- Access master node container

```shell
# Master Node
docker exec -it multi-node-cluster-control-plane bash
# Worker Node
docker exec -it multi-node-cluster-worker bash
```

- See kubernetes components running on the master node

```shell
ps aux 
#OR
ps -ef | grep kube
```

- Master node k8s component

```text
1. kube-apiserver
2. kube-controller-manager
3. kube-scheduler
4. etcd
5. kubelet 
6. kube-proxy
```

- Worker node k8s component

```text
1. kubelet
2. kube-proxy
3. container runtime - containerd - docker
```

### Kubelet

```text
Agent running on all nodes report status of nodes and pods to master node
```

- Check kubelet status - after login into the node

```shell
systemctl status kubelet
```

- make problem into kubelet by move its configuration file

```text
mkdir /temp/
mv /etc/kubernetes/kubelet.conf /temp/
```

- Restart kubelet process

```shell
systemctl restart kubelet
```

- Check the node - Node status will be NotReady

```shell
kubectl get nodes
 ```

```text
# Node status will be NotReady because api-server not able to access to kubelet.
# Scheduler will not be able to assign pods on this node again.

NAME                               STATUS     ROLES           AGE   VERSION
multi-node-cluster-control-plane   Ready      control-plane   19h   v1.27.3
multi-node-cluster-worker          NotReady   <none>          19h   v1.27.3
multi-node-cluster-worker2         Ready      <none>          19h   v1.27.3
```

- Solve the issue

```shell
mv /temp/kubelet.conf /etc/kubernetes/kubelet.conf
systemctl restart kubelet
systemctl status kubelet
```

```text
NAME                               STATUS     ROLES           AGE   VERSION
multi-node-cluster-control-plane   Ready      control-plane   19h   v1.27.3
multi-node-cluster-worker          Ready      <none>          19h   v1.27.3
multi-node-cluster-worker2         Ready      <none>          19h   v1.27.3
```

- Get pods running on a node

```shell
kubectl get pods  -o wide --field-selector spec.nodeName=multi-node-cluster-worker
```

### Node Maintenance

- Cordoning a node

```shell
# Cordoning a node will prevent scheduler from assigning new pods to this node
kubectl cordon multi-node-cluster-worker
```

- Drain OR Migrate all pods from a node

```shell
# Migrate all pods from a node to another node
kubectl drain multi-node-cluster-worker --ignore-daemonsets
```

- After maintenance - uncordon the node

```shell
kubectl uncordon multi-node-cluster-worker
```

## Resources

```text
Resource Quota - Quality of Service -  Limit Range
```

## Pods

```text
Pod should not be a code runs for one time it will lead to CrashLoopBackOf or restart.
Pod should be running process like web server or database.
```

- Check Pod running on which node.

```shell
kubectl get pods -o wide
```

- Create Multi-Container Pod

```shell
kubectl create -f pod/test-pod3.yml
```

- Get logs from specific container

```shell
 kubectl logs two-containers-pod -c k8s-test
```

- Access container shell

```shell
kubectl exec -it two-containers-pod -c k8s-test sh
```

- Make file inside the container

```shell
kubectl exec -it two-containers-pod -c k8s-test sh
touch /tmp/test.txt
```

- Copy file inside the container

```shell 
# not working
kubectl cp E:\k8s\k8s\README.md two-containers-pod:/temp/README.md
```

### Readiness Probe

```text
Readiness probes are used to know when a pod is ready to serve traffic.
Readiness probes are defined in the pod spec.
```

- Example

Spring Boot Actuator provides a number of additional features to help you monitor and manage your application when it’s
pushed to production.

Use the /actuator/health endpoint to get basic application health information.

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: k8s-test
spec:
  containers:
    - name: k8s-test
    image: ahmedgodaa/k8s-test:v1.0.1
    imagePullPolicy: IfNotPresent
    ports:
      - containerPort: 8080
    readinessProbe:
      httpGet:
      path: /actuator/health
      port: 8080
      httpHeaders:
        - name: Custom-Header
          value: to-ke-en-ex-am-pl-e
      initialDelaySeconds: 5
      periodSeconds: 5
  ```

### Liveness Probe

**liveness probe not implemented yet**
[Reference](https://spring.io/blog/2020/03/25/liveness-and-readiness-probes-with-spring-boot)

```text
Liveness probes are used to know when a pod is alive or dead.
Liveness probes are defined in the pod spec.
Once the liveness probe fails a pod will be restarted.
```

## Multi-Container Pods

```text
Multi-Container Pods are pods that contain more than one container.
Multi-Container Pods are used in cases where multiple processes need to work together.
```

- Create Multi-Container Pod

```shell
# both containers have shared mount volume
kubectl create -f pod/test-pod4.yml
```

- Access the pod shell

```shell
kubectl exec -it sidecar-container-demo sh
```

- Use bash

```shell
bash
```

- Access Created Html file which created by debian container in the nginx container.

```shell
# this html file will be deployed on the nginx container
cd /usr/share/nginx/html
cat index.html
```

## Resource Quota

```text
ResourceQuota is a policy resource that specifies 
compute resource requirements (such as memory and CPU) 
and object count limits 
(such as maximum count of pods per namespace) for namespaces.
```

### Quality of Service

```text
Quality of Service (QoS) is a concept in Kubernetes that allows the cluster to make intelligent scheduling decisions.
QoS is three classes:
    Guaranteed 
    Burstable
    BestEffort
Guaranteed: 
    The pod is guaranteed to have the requested resources available at all times.
    If the node fails, the pod is guaranteed to be restarted on a node with the requested resources.
    Guaranteed pods are the highest priority in the system.
    If the cluster runs out of resources, the Guaranteed pods are the last to be evicted.
```

- Guaranteed

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: guaranteed-pod
  namespace: default
spec:
  containers:

  ```

- Burstable

```text
Burstable: 
    The pod is not guaranteed to have requested resources available at all times.
    If the node fails, the pod may not be restarted on a node with the requested resources.
    Burstable pods are the second highest priority in the system.
    If the cluster runs out of resources, the Burstable pods are the second to be evicted.
```

- BestEffort

```text
BestEffort: 
    The pod is not guaranteed to have requested resources available at all times.
    If the node fails, the pod may not be restarted on a node with the requested resources.
    BestEffort pods are the lowest priority in the system.
    If the cluster runs out of resources, the BestEffort pods are the first to be evicted.
```

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: besteffort-pod
  namespace: default
```

### Network Policy

```text
How a pod is allowed to communicate with various network "entities" (other pods, Service endpoints, external IPs, etc).
```

## Secrets

#### Secrets Types

1. Opaque
2. Service Account Token
3. Basic Auth
4. SSH
5. TLS - tls.crt - tls.key
6. Docker Config
7. Bootstrap Token

- Create Secret from ssh key

```shell
kubectl create secret generic ssh-key-secret --from-file=ssh-privatekey=.ssh/id_rsa --from-file=ssh-publickey=.ssh/id_rsa.pub
```

- Create Secret from env

```shell
  # Create a new secret named my-secret from env files
kubectl create secret generic my-secret --from-env-file=path/to/foo.env --from-env-file=path/to/bar.env
  # Create a new secret named my-secret from env values
kubectl create secret generic test-secret --from-literal=ENVIRONMENT=development --from-literal=PORT=8080
```

- Create Secret for TLS certificate

```shell
kubectl create secret tls tls-secret --cert=tls.cert --key=tls.key
```

## ConfigMap

A ConfigMap provides a way to inject configuration data into pods. The data stored in a ConfigMap consumed by
containerized applications running in a pod.

- Create configmap from file

```shell
kubectl create configmap test-config --from-file=configmap/application-prop-test.yml
```

##### ConfigMap Values shapes

1. Property like key
2. File

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: test-config-map
data:
  # property like key
  port: 8080
  environment: development
```

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: configmap-demo-pod
spec:
  containers:
    - name: k8s-test-container
      image: ahmedgodaa/k8s-test:v1.0.0
      env:
        - name: ENVIRONMENT
          valueFrom:
            configMapKeyRef:
              name: test-config-map
              key: environment
```

## Secrets with ConfigMaps

There is two ways to consume secrets and configmaps into the pod

1. **Key Value Pairs**
2. **Files Mounted inside the pod**

> `📝` **Note**:
> 1. ConfigMap can be used as environment variables or as files in a volume.
> 2. To Use File in the configmap you should use `volumeMounts` and `volumes` in the pod spec to mount the files.

#### Key Value Pairs

Use the configmap and secret direct as environment variables into the pod

- Create ConfigMap

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: test-config-map
data:
  port: 8080
```

- Create Secret

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: test-secret
type: Opaque
data:
  password: admin
```

- Consume created configmap and secret into the pod.

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: configmap-demo-pod
spec:
containers:
  - name: k8s-test-container
  image: ahmedgodaa/k8s-test:v1.0.0
  ports:
    - containerPort: 8080
  env:
    - name: PASSWORD
      valueFrom:
        secretKeyRef:
        name: test-secret
        key: password
    - name: PORT
    valueFrom:
      configMapKeyRef:
      name: test-config-map
      key: port
```

#### Files

Mount the configmap and secret as files into the pod and the Container.

- Create ConfigMap with file

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: test-config-map
data:
  #   the name of the file and the content
  application-prop-test.yml: |-
    port: 8080
    environment: development
```

- Create Secret with file

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: test-secret
type: Opaque
data:
  secret.file: |-
    thepasswordexampleassecret
```

- Mount into the Pod

1. Specify the configmap and secret into the volume this will make it accessible inside the pod.
2. It should be accessible inside the container also.
3. Specify the volumeMounts in the container to mount the volume into the container.

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: configmap-demo-pod
spec:
  containers:
    - name: k8s-test-container
    image: ahmedgodaa/k8s-test:v1.0.0
    #  where the configmap and secret will be saved inside the container
    volumeMounts:
      - name: config-volume
        mountPath: /config
        readOnly: true

      - name: secret-volume
        mountPath: /secret
        readOnly: true
  #  create a volume to store the configmap
  volumes:
    #   volume name
    - name: config-volume
      #      the type of volume will be mounted - configmap
      configMap:
        name: test-config-map
    #   volume name
    - name: secret-volume
      #      the type of volume will be mounted - secret
      secret:
        secretName: test-secret

```


