# Installation

## Download K8s

1. Download **Api-Server**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-apiserver"
chmod +x kube-apiserver
```

2. Download **Controller-Manager**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-controller-manager"
chmod +x kube-controller-manager
```

3. Download **Scheduler**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-scheduler"
chmod +x kube-scheduler
```

4. Download **Kube-Proxy**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kube-proxy"
chmod +x kube-proxy
```

5. Download **Kubelet**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kubelet"
chmod +x kubelet
```

6. Download **Kubectl**

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/amd64/kubectl"
chmod +x kubectl
```

7. Download **etcd**

```shell
curl -LO "https://github.com/etcd-io/etcd/releases/download/v3.5.9/etcd-v3.5.9-linux-amd64.tar.gz"
```
* **After download all files should be executable**

![image](https://github.com/AhmedGodaa/k8s/assets/73083104/88867d6d-16e8-460d-82a2-09e15aef3dc5)

