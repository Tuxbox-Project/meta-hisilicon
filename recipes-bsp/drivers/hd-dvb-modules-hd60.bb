KV = "4.4.35"
SRCDATE = "20190117"

PROVIDES = "virtual/blindscan-dvbs"
PROVIDES  = "virtual/dvb-driver"
RPROVIDES_${PN} = "virtual/dvb-driver"
RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
	
require hd-dvb-modules.inc


SRC_URI[md5sum] = "ef150875b95e26ce1488926ef747c5b9"
SRC_URI[sha256sum] = "f852e03fb42112cbb219a3a6d83a250e6dbbc57c7948c3e917e10db559bc2e1c"

do_configure[noexec] = "1"

do_install_append() {
	install -d ${D}${bindir}
}

do_package_qa() {
}

FILES_${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} += "already-stripped"
