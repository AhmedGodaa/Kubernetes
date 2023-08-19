# Installation

## Download K8s

1. Install Api-Server

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/arm64/kube-apiserver"
```

2. Install Controller-Manager

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/arm64/kube-controller-manager"
```

3. Install Scheduler

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/arm64/kube-scheduler"
```

4. Install Kube-Proxy

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/arm64/kube-proxy"
```

5. Install Kubelet

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/arm64/kubelet"
```

6. Install Kubectl

```shell
curl -LO "https://dl.k8s.io/v1.27.4/bin/linux/arm64/kubectl"
```

7. Install etcd

```shell
     curl -LO "https://github.com/etcd-io/etcd/releases/download/v3.5.9/etcd-v3.5.9-linux-arm64.tar.gz"
```