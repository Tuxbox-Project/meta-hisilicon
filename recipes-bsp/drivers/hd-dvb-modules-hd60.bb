KV = "4.4.35"
SRCDATE = "20180918"

PROVIDES = "virtual/blindscan-dvbs"
PROVIDES  = "virtual/dvb-driver"
RPROVIDES_${PN} = "virtual/dvb-driver"

require hd-dvb-modules.inc

SRC_URI[md5sum] = "4336962377af608ebd0b666e210b8a5c"
SRC_URI[sha256sum] = "e36e973164528d774031e92a43fd687ac7c4121773320e113c963a0d734544eb"

do_configure[noexec] = "1"

do_install_append() {
	install -d ${D}${bindir}
}

do_package_qa() {
}

FILES_${PN} += " ${bindir} ${sysconfdir}/init.d"

INSANE_SKIP_${PN} += "already-stripped"
