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

SRCREV_machine_qemuarm ?= "51fc1677b3378d04bf6bc59f632d80c8a21e54fe"
SRCREV_machine_qemuarm64 ?= "5c9319fd04d643d55836b94f7eeb4dd7561ea197"
SRCREV_machine_qemumips ?= "78b65359d44c9ae535388dfc8bf06eb8dc8764dc"
SRCREV_machine_qemuppc ?= "dd1a7ce7eb8274e72507d634d9239109fdbebb85"
SRCREV_machine_qemuriscv64 ?= "0f586f4ee8adacac79b64d1f3d47799a5eb7fbea"
SRCREV_machine_qemuriscv32 ?= "0f586f4ee8adacac79b64d1f3d47799a5eb7fbea"
SRCREV_machine_qemux86 ?= "0f586f4ee8adacac79b64d1f3d47799a5eb7fbea"
SRCREV_machine_qemux86-64 ?= "0f586f4ee8adacac79b64d1f3d47799a5eb7fbea"
SRCREV_machine_qemumips64 ?= "a6f8486df096f727ec6233a152947d9bf26202bb"
SRCREV_machine ?= "0f586f4ee8adacac79b64d1f3d47799a5eb7fbea"
SRCREV_meta ?= "e532b0393413badf4f0df4b2337015efd8eae932"

# set your preferred provider of linux-yocto to 'linux-yocto-upstream', and you'll
# get the <version>/base branch, which is pure upstream -stable, and the same
# meta SRCREV as the linux-yocto-standard builds. Select your version using the
# normal PREFERRED_VERSION settings.
BBCLASSEXTEND = "devupstream:target"
SRCREV_machine_class-devupstream ?= "545aecd229613138d6db54fb2b5221faca10137f"
PN_class-devupstream = "linux-yocto-upstream"
KBRANCH_class-devupstream = "v5.15/base"

SRC_URI = "git://git.yoctoproject.org/linux-yocto.git;name=machine;branch=${KBRANCH}; \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-5.15;destsuffix=${KMETA}"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION ?= "5.15.52"

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
KERNEL_FEATURES_append_powerpc =" arch/powerpc/powerpc-debug.scc"
KERNEL_FEATURES_append_powerpc64 =" arch/powerpc/powerpc-debug.scc"
KERNEL_FEATURES_append_powerpc64le =" arch/powerpc/powerpc-debug.scc"

INSANE_SKIP_kernel-vmlinux_qemuppc64 = "textrel"

# devupstream for this case is broken in dunfell and requires later fix
# from commit d0edb03088d0d1c20c899daed1bb3a7110b19670
BBCLASSEXTEND_remove = "devupstream:target"
