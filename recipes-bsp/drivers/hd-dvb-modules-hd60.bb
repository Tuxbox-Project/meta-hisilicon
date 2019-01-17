KV = "4.4.35"
SRCDATE = "20190116"

PROVIDES = "virtual/blindscan-dvbs"
PROVIDES  = "virtual/dvb-driver"
RPROVIDES_${PN} = "virtual/dvb-driver"
RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
	
require hd-dvb-modules.inc

SRC_URI[md5sum] = "f6e9a23a22f94334a8f9bc230933dbb3"
SRC_URI[sha256sum] = "ee4ef70f4a87709152fd8d017894773f9e28ab701da324cb8067023f79e1a14d"

do_configure[noexec] = "1"

do_install_append() {
	install -d ${D}${bindir}
}

do_package_qa() {
}

FILES_${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} += "already-stripped"
