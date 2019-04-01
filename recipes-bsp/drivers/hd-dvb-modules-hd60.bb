KV = "4.4.35"
SRCDATE = "20190319"

PROVIDES = "virtual/blindscan-dvbs"
PROVIDES  = "virtual/dvb-driver"
RPROVIDES_${PN} = "virtual/dvb-driver"
RDEPENDS_${PN} = "libjpeg-turbo pulseaudio-lib-rtp"
	
require hd-dvb-modules.inc

SRC_URI[md5sum] = "05362cd64a869fa0a57c9d5605de4615"
SRC_URI[sha256sum] = "090085f31895e7e7cccd8d50d692040f33455f56fa844521a64689d864e00d5b"

do_configure[noexec] = "1"

do_install_append() {
	install -d ${D}${bindir}
}

do_package_qa() {
}

FILES_${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} += "already-stripped"
