RDEPENDS_${PN}  += "virtual/libgles2"

FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

DEPENDS += "mesa virtual/libgles2 drm"

PACKAGECONFIG_remove = "${PACKAGECONFIG_GL}"

PACKAGECONFIG_DEFAULT_append = "freetype fontconfig eglfs release optimize-size gles2 openssl journald libinput xkbcommon"


INSANE_SKIP_${PN} +="file-rdeps"
INSANE_SKIP_${PN}-plugins +="file-rdeps"


SET_QT_QPA_DEFAULT_PLATFORM = "eglfs"
SET_QT_QPA_EGLFS_INTEGRATION = "eglfs_mali"

do_configure_prepend() {
cat >> ${S}/mkspecs/oe-device-extra.pri <<EOF
QT_QPA_DEFAULT_PLATFORM = ${SET_QT_QPA_DEFAULT_PLATFORM}
EGLFS_DEVICE_INTEGRATION = ${SET_QT_QPA_EGLFS_INTEGRATION}
QT_QPA_EGLFS_INTEGRATION = ${SET_QT_QPA_EGLFS_INTEGRATION}
EOF
}

