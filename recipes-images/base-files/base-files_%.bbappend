FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append += " \
	file://firstboot.sh \
	file://flash \
	file://imgbackup \
	file://mount.sh \
	file://mnt-userdata.mount \
	file://boot.mount \
	file://partitions-by-name.sh \
	file://partitions-by-name.service \
"

do_install_append() {
	install -d ${D}${bindir} ${D}${sbindir} ${D}${systemd_unitdir}/system/multi-user.target.wants
 	install -m 0755 ${WORKDIR}/firstboot.sh  ${D}${sbindir}
	install -m 0755 ${WORKDIR}/partitions-by-name.sh ${D}${bindir}
        install -m 0644 ${WORKDIR}/partitions-by-name.service ${D}${systemd_unitdir}/system
        ln -sf /lib/systemd/system/partitions-by-name.service ${D}${systemd_unitdir}/system/multi-user.target.wants
	install -m 0644 ${WORKDIR}/mnt-userdata.mount ${D}${systemd_unitdir}/system
        ln -sf /lib/systemd/system/mnt-userdata.mount ${D}${systemd_unitdir}/system/multi-user.target.wants
        install -m 0644 ${WORKDIR}/boot.mount ${D}${systemd_unitdir}/system
        ln -sf /lib/systemd/system/boot.mount ${D}${systemd_unitdir}/system/multi-user.target.wants
	install -m 0755 ${WORKDIR}/flash ${D}${bindir}
	install -m 0755 ${WORKDIR}/imgbackup ${D}${bindir}
        install -m 0755 ${WORKDIR}/mount.sh ${D}${bindir}
}
