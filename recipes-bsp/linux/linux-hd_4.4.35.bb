DESCRIPTION = "Linux kernel for ${MACHINE}"
SECTION = "kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
VER ?= "${@bb.utils.contains('TARGET_ARCH', 'aarch64', '64' , '', d)}"

KERNEL_VERSION = "4.4.35"

SRCDATE = "20181228"
COMPATIBLE_MACHINE = "(hd60)"

inherit kernel machine_kernel_pr

SRC_URI[arm.md5sum] = "ede25f1c2c060f1059529a2896cee5a9"
SRC_URI[arm.sha256sum] = "ea4ba0433d252c18f38ff2f4dce4b70880e447e1cffdc2066d5a9b5f8098ae7e"

SRC_URI = "http://downloads.mutant-digital.net/linux-${PV}-${SRCDATE}-${ARCH}.tar.gz;name=${ARCH} \
	file://defconfig \
	file://dont-mark-register-as-const.patch \
	file://ieee80211-increase-scan-result-expire-time.patch \
	file://give-up-on-gcc-ilog2-constant-optimizations.patch \
	file://0001-remote.patch \
	file://initramfs-subdirboot.cpio.gz;unpack=0 \
"

# By default, kernel.bbclass modifies package names to allow multiple kernels
# to be installed in parallel. We revert this change and rprovide the versioned
# package names instead, to allow only one kernel to be installed.
PKG_${KERNEL_PACKAGE_NAME}-base = "kernel-base"
PKG_${KERNEL_PACKAGE_NAME}-image = "kernel-image"
RPROVIDES_PKG_${KERNEL_PACKAGE_NAME}-base = "kernel-${KERNEL_VERSION}"
RPROVIDES_PKG_${KERNEL_PACKAGE_NAME}-image = "kernel-image-${KERNEL_VERSION}"

S = "${WORKDIR}/linux-${PV}"

export OS = "Linux"
KERNEL_OBJECT_SUFFIX = "ko"
KERNEL_IMAGETYPE = "uImage"
KERNEL_OUTPUT = "arch/${ARCH}/boot/${KERNEL_IMAGETYPE}"

FILES_${KERNEL_PACKAGE_NAME}-image = "/tmp"

kernel_do_configure_prepend() {
    install -d ${B}/usr
    install -m 0644 ${WORKDIR}/initramfs-subdirboot.cpio.gz ${B}/
}

kernel_do_install_append() {
        install -d ${D}/tmp
        install -m 0755 ${KERNEL_OUTPUT} ${D}/tmp
}

pkg_postinst_on_target_kernel-image() {
	[ -f /tmp/${KERNEL_IMAGETYPE} ] && dd if=/tmp/${KERNEL_IMAGETYPE} of=/dev/mmcblk0p19
}
