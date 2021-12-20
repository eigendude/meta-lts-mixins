KBRANCH ?= "v5.15/standard/base"

require recipes-kernel/linux/linux-yocto.inc

# board specific branches
KBRANCH_qemuarm  ?= "v5.15/standard/arm-versatile-926ejs"
KBRANCH_qemuarm64 ?= "v5.15/standard/qemuarm64"
KBRANCH_qemumips ?= "v5.15/standard/mti-malta32"
KBRANCH_qemuppc  ?= "v5.15/standard/qemuppc"
KBRANCH_qemuriscv64  ?= "v5.15/standard/base"
KBRANCH_qemuriscv32  ?= "v5.15/standard/base"
KBRANCH_qemux86  ?= "v5.15/standard/base"
KBRANCH_qemux86-64 ?= "v5.15/standard/base"
KBRANCH_qemumips64 ?= "v5.15/standard/mti-malta64"

SRCREV_machine_qemuarm ?= "6ef10661ed1b3f14bd424f5a533f431c3121ae1f"
SRCREV_machine_qemuarm64 ?= "81abcbb980529f94d9630358ecffbabd6a21e149"
SRCREV_machine_qemumips ?= "c2d607d67a1c0231b63bbbbd7eb490342fd48ade"
SRCREV_machine_qemuppc ?= "81abcbb980529f94d9630358ecffbabd6a21e149"
SRCREV_machine_qemuriscv64 ?= "81abcbb980529f94d9630358ecffbabd6a21e149"
SRCREV_machine_qemuriscv32 ?= "81abcbb980529f94d9630358ecffbabd6a21e149"
SRCREV_machine_qemux86 ?= "81abcbb980529f94d9630358ecffbabd6a21e149"
SRCREV_machine_qemux86-64 ?= "81abcbb980529f94d9630358ecffbabd6a21e149"
SRCREV_machine_qemumips64 ?= "31c1532611489b9fdc807d9cec8ff3b727d5f9fe"
SRCREV_machine ?= "81abcbb980529f94d9630358ecffbabd6a21e149"
SRCREV_meta ?= "eeb5d0c9dd5e2928835c633644426ee357fbce12"

# set your preferred provider of linux-yocto to 'linux-yocto-upstream', and you'll
# get the <version>/base branch, which is pure upstream -stable, and the same
# meta SRCREV as the linux-yocto-standard builds. Select your version using the
# normal PREFERRED_VERSION settings.
BBCLASSEXTEND = "devupstream:target"
DEFAULT_PREFERENCE_class-devupstream = "-1"
SRCREV_machine_class-devupstream ?= "57dcae4a8b93271c4e370920ea0dbb94a0215d30"
PN_class-devupstream = "linux-yocto-upstream"
KBRANCH_class-devupstream = "v5.15/base"

# remap qemuarm to qemuarma15 for the 5.8 kernel
# KMACHINE_qemuarm ?= "qemuarma15"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.15;destsuffix=${KMETA}"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION ?= "5.15.10"

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
KERNEL_FEATURES_append = " ${@bb.utils.contains("TUNE_FEATURES", "mx32", " cfg/x32.scc", "", d)}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/scsi/scsi-debug.scc", "", d)}"
KERNEL_FEATURES_append = " ${@bb.utils.contains("DISTRO_FEATURES", "ptest", " features/gpio/mockup.scc", "", d)}"

# devupstream for this case is broken in dunfell and requires later fix
# from commit d0edb03088d0d1c20c899daed1bb3a7110b19670
BBCLASSEXTEND_remove = "devupstream:target"
