SRCDATE = "20200622"

DESCRIPTION = "${MACHINE} libraries"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
PR = "${SRCDATE}"
PACKAGE_ARCH = "${MACHINE_ARCH}"
VER ?= "${@bb.utils.contains('TARGET_ARCH', 'aarch64', '64' , '', d)}"

COMPATIBLE_MACHINE = "hd60|hd61"
SRCDATE = "20200622"

SRC_URI = "http://downloads.mutant-digital.net/${MACHINE}/${MACHINE}-libs${VER}-${SRCDATE}.zip"
SRC_URI[sha256sum] = "37a9e197391acd4f95a7d7bcef3199cfa4f42b255efe65536d2de58d9f3a57a4"

S = "${WORKDIR}"

INHIBIT_PACKAGE_STRIP = "1"

# Disable anything auto depending on hisilicon libs
EXCLUDE_FROM_SHLIBS = "1"
	
# The driver is a set of binary libraries to install
# there's nothing to configure or compile
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	install -d ${D}${libdir}/hisilicon
	install -m 0755 ${S}/hisilicon/* ${D}${libdir}/hisilicon
	install -m 0755 ${S}/ffmpeg/* ${D}${libdir}/hisilicon
	ln -sf ${base_libdir}/ld-linux-armhf.so.3 ${D}${libdir}/hisilicon/ld-linux.so
}

do_package_qa() {
}

FILES_${PN} = "${libdir}/hisilicon/*"
FILES_${PN}-dev = ""
	
INSANE_SKIP_${PN} += "ldflags already-stripped dev-so"
