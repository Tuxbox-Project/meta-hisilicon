KV = "4.4.35"
SRCDATE = "20190201"

PROVIDES = "virtual/blindscan-dvbs"
PROVIDES  = "virtual/dvb-driver"
RPROVIDES_${PN} = "virtual/dvb-driver"
RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
	
require hd-dvb-modules.inc

SRC_URI[md5sum] = "cf1a7e20e733212299fc58293a728b27"
SRC_URI[sha256sum] = "d0edfd90d2372b2a4326673dbe57f50fd6e8b726741fde8c7baf5bb114a4bafa"

do_configure[noexec] = "1"

do_install_append() {
	install -d ${D}${bindir}
}

do_package_qa() {
}

FILES_${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} += "already-stripped"
