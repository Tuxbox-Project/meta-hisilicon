FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

SRC_URI_append += "file://0001-fix-eglfs-mali-plugin-build.patch"

DEPENDS += "mesa hd-mali-hd60"

do_configure_prepend(){
cat >> ${S}/mkspecs/linux-oe-g++/qmake.conf <<EOF
EGLFS_DEVICE_INTEGRATION = eglfs_mali
EOF
}
