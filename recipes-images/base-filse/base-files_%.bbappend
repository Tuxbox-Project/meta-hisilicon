FILESEXTRAPATHS_prepend := "${THISDIR}/files:"


SRC_URI_append += " \
	file://local.sh \
	file://firstboot.sh \
	file://mount.sh \
"

do_install_append() {
	install -d ${D}${bindir} ${D}${sbindir}
	install -m 0755 ${WORKDIR}/firstboot.sh  ${D}${sbindir}
	install -m 0755 ${WORKDIR}/local.sh ${D}${bindir}	
	install -m 0755 ${WORKDIR}/mount.sh ${D}${bindir}
}

