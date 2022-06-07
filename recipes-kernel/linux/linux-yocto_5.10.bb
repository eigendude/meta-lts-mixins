KBRANCH ?= "v5.10/standard/base"

require recipes-kernel/linux/linux-yocto.inc

# board specific branches
KBRANCH_qemuarm  ?= "v5.10/standard/arm-versatile-926ejs"
KBRANCH_qemuarm64 ?= "v5.10/standard/qemuarm64"
KBRANCH_qemumips ?= "v5.10/standard/mti-malta32"
KBRANCH_qemuppc  ?= "v5.10/standard/qemuppc"
KBRANCH_qemuriscv64  ?= "v5.10/standard/base"
KBRANCH_qemuriscv32  ?= "v5.10/standard/base"
KBRANCH_qemux86  ?= "v5.10/standard/base"
KBRANCH_qemux86-64 ?= "v5.10/standard/base"
KBRANCH_qemumips64 ?= "v5.10/standard/mti-malta64"

SRCREV_machine_qemuarm ?= "68264cfbddebea663543a7c4ad5131c6cf63d3d2"
SRCREV_machine_qemuarm64 ?= "b95be3e8c15d939ce402775de98ab80eda493b11"
SRCREV_machine_qemumips ?= "c6a8eaf00384dcce14bb9e28f2d68b5004e8c6f3"
SRCREV_machine_qemuppc ?= "6c1e46f34c6b9ababf8c6fcb4c01274099bb034f"
SRCREV_machine_qemuriscv64 ?= "f844c3765c3270321f0b3347992565cfdb938c99"
SRCREV_machine_qemuriscv32 ?= "f844c3765c3270321f0b3347992565cfdb938c99"
SRCREV_machine_qemux86 ?= "f844c3765c3270321f0b3347992565cfdb938c99"
SRCREV_machine_qemux86-64 ?= "f844c3765c3270321f0b3347992565cfdb938c99"
SRCREV_machine_qemumips64 ?= "8d66b3ad7fbc8554ba2248cfbe755f8d24cb5a1a"
SRCREV_machine ?= "f844c3765c3270321f0b3347992565cfdb938c99"
SRCREV_meta ?= "2f6fa8da5f84c343e6ea57c76829eaca1cc6a840"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.10;destsuffix=${KMETA}"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION ?= "5.10.119"

DEPENDS += "${@bb.utils.contains('ARCH', 'x86', 'elfutils-native', '', d)}"
DEPENDS += "openssl-native util-linux-native"
DEPENDS += "gmp-native libmpc-native"

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"

KERNEL_DEVICETREE_qemuarmv5 = "versatile-pb.dtb"

COMPATIBLE_MACHINE = "qemuarm|qemuarmv5|qemuarm64|qemux86|qemuppc|qemuppc64|qemumips|qemumips64|qemux86-64|qemuriscv64|qemuriscv32"

# Functionality flags
KERNEL_EXTRA_FEATURES ?= "features/netfilter/netfilter.scc"
KERNEL_FEATURES_append = " ${KERNEL_EXTRA_FEATURES}"
KERNEL_FEATURES_append_qemuall=" cfg/virtio.scc features/drm-bochs/drm-bochs.scc"
KERNEL_FEATURES_append_qemux86=" cfg/sound.scc cfg/paravirt_kvm.scc"
KERNEL_FEATURES_append_qemux86-64=" cfg/sound.scc cfg/paravirt_kvm.scc"
KERNEL_FEATURES_append_powerpc =" arch/powerpc/powerpc-debug.scc"
KERNEL_FEATURES_append_powerpc64 =" arch/powerpc/powerpc-debug.scc"
KERNEL_FEATURES_append_powerpc64le =" arch/powerpc/powerpc-debug.scc"
KERNEL_FEATURES_append = " ${@bb.utils.contains("TUNE_FEATURES", "mx32", " cfg/x32.scc", "", d)}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/scsi/scsi-debug.scc", "", d)}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/gpio/mockup.scc", "", d)}"
