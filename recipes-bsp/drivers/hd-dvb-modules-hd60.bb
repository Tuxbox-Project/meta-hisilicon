KV = "4.4.35"
SRCDATE = "20181228"

PROVIDES = "virtual/blindscan-dvbs"
PROVIDES  = "virtual/dvb-driver"
RPROVIDES_${PN} = "virtual/dvb-driver"

require hd-dvb-modules.inc

SRC_URI[md5sum] = "cb9a57497a823b6cee255ff1d75f312d"
SRC_URI[sha256sum] = "f3541ead8c6865c8fa7ca0fcb835ee60ae35dc3fff1b861c42eb7731188a022d"

do_configure[noexec] = "1"

do_install_append() {
	install -d ${D}${bindir}
}

do_package_qa() {
}

FILES_${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} += "already-stripped"
