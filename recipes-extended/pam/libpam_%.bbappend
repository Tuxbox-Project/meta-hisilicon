do_install_append() {
	echo "QT_QPA_EGLFS_INTEGRATION=eglfs_mali" >> ${D}${sysconfdir}/environment
}
