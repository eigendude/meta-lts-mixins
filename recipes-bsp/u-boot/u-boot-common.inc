HOMEPAGE = "http://www.denx.de/wiki/U-Boot/WebHome"
DESCRIPTION = "U-Boot, a boot loader for Embedded boards based on PowerPC, \
ARM, MIPS and several other processors, which can be installed in a boot \
ROM and used to initialize and test the hardware or to download and run \
application code."
SECTION = "bootloaders"
DEPENDS += "flex-native bison-native python3-setuptools-native"

LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=2ca5f2c35c8cc335f0a19756634782f1"
PE = "1"

# We use the revision in order to avoid having to fetch it from the
# repo during parse
SRCREV = "080bb8480a4e915b1ef52418a9b7a227c75a22ee"

SRC_URI = "git://source.denx.de/u-boot/u-boot.git;protocol=https;branch=master"

# Apply patches
FILESEXTRAPATHS:prepend := "${THISDIR}/u-boot:"
SRC_URI:append = " \
    file://0001-Add-RPi-5-device-tree.patch \
    file://0002-Add-hack-for-RPi-5.patch \
"

S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

inherit pkgconfig

do_configure[cleandirs] = "${B}"