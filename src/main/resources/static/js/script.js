$(document).ready(function() {
    const API_URL = 'http://localhost:8080/ktp';
    let isEditing = false;

    // Initial Load
    loadKtpData();

    // Form Submission (Add or Update)
    $('#ktp-form').on('submit', function(e) {
        e.preventDefault();

        const ktpId = $('#ktp-id').val();
        const ktpData = {
            nomorKtp: $('#nomorKtp').val(),
            namaLengkap: $('#namaLengkap').val(),
            alamat: $('#alamat').val(),
            tanggalLahir: $('#tanggalLahir').val(),
            jenisKelamin: $('#jenisKelamin').val()
        };

        if (isEditing) {
            updateKtp(ktpId, ktpData);
        } else {
            createKtp(ktpData);
        }
    });

    // Cancel Edit
    $('#cancel-btn').on('click', function() {
        resetForm();
    });

    // Search Functionality
    $('#search-input').on('keyup', function() {
        const value = $(this).val().toLowerCase();
        $("#ktp-list tr").filter(function() {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

    // Load Data function
    function loadKtpData() {
        $.ajax({
            url: API_URL,
            method: 'GET',
            success: function(data) {
                renderTable(data);
            },
            error: function(err) {
                showToast('Gagal memuat data!', 'error');
                console.error(err);
            }
        });
    }

    // Create function
    function createKtp(data) {
        $.ajax({
            url: API_URL,
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function() {
                showToast('Data berhasil ditambahkan!', 'success');
                resetForm();
                loadKtpData();
            },
            error: function(err) {
                const message = err.responseJSON ? err.responseJSON.message : 'Gagal menambah data!';
                showToast(message, 'error');
            }
        });
    }

    // Update function
    function updateKtp(id, data) {
        $.ajax({
            url: `${API_URL}/${id}`,
            method: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function() {
                showToast('Data berhasil diperbarui!', 'success');
                resetForm();
                loadKtpData();
            },
            error: function(err) {
                const message = err.responseJSON ? err.responseJSON.message : 'Gagal memperbarui data!';
                showToast(message, 'error');
            }
        });
    }

    // Delete function (global for onclick)
    window.deleteKtp = function(id) {
        if (confirm('Apakah Anda yakin ingin menghapus data ini?')) {
            $.ajax({
                url: `${API_URL}/${id}`,
                method: 'DELETE',
                success: function() {
                    showToast('Data berhasil dihapus!', 'success');
                    loadKtpData();
                },
                error: function() {
                    showToast('Gagal menghapus data!', 'error');
                }
            });
        }
    };

    // Edit function (global for onclick)
    window.editKtp = function(id) {
        $.ajax({
            url: `${API_URL}/${id}`,
            method: 'GET',
            success: function(data) {
                $('#ktp-id').val(data.id);
                $('#nomorKtp').val(data.nomorKtp);
                $('#namaLengkap').val(data.namaLengkap);
                $('#alamat').val(data.alamat);
                $('#tanggalLahir').val(data.tanggalLahir);
                $('#jenisKelamin').val(data.jenisKelamin);

                isEditing = true;
                $('#form-title').html('<i class="fa-solid fa-edit"></i> Edit Data KTP');
                $('#submit-btn span').text('Perbarui Data');
                $('#cancel-btn').removeClass('hidden');
                
                // Scroll to form
                $('html, body').animate({
                    scrollTop: $(".form-section").offset().top - 20
                }, 500);
            },
            error: function() {
                showToast('Gagal mengambil data KTP!', 'error');
            }
        });
    };

    function renderTable(data) {
        const tbody = $('#ktp-list');
        tbody.empty();

        if (data.length === 0) {
            tbody.append('<tr><td colspan="7" class="loading">Tidak ada data.</td></tr>');
            return;
        }

        data.forEach((ktp, index) => {
            tbody.append(`
                <tr>
                    <td>${index + 1}</td>
                    <td><span class="badge">${ktp.nomorKtp}</span></td>
                    <td><strong>${ktp.namaLengkap}</strong></td>
                    <td>${ktp.alamat}</td>
                    <td>${formatDate(ktp.tanggalLahir)}</td>
                    <td>${ktp.jenisKelamin}</td>
                    <td>
                        <div class="actions">
                            <button class="btn-icon btn-edit" onclick="editKtp(${ktp.id})">
                                <i class="fa-solid fa-pen-to-square"></i>
                            </button>
                            <button class="btn-icon btn-delete" onclick="deleteKtp(${ktp.id})">
                                <i class="fa-solid fa-trash"></i>
                            </button>
                        </div>
                    </td>
                </tr>
            `);
        });
    }

    function resetForm() {
        $('#ktp-form')[0].reset();
        $('#ktp-id').val('');
        isEditing = false;
        $('#form-title').html('<i class="fa-solid fa-plus-circle"></i> Tambah Data KTP');
        $('#submit-btn span').text('Simpan Data');
        $('#cancel-btn').addClass('hidden');
    }

    function showToast(message, type) {
        const toast = $('#toast');
        toast.text(message);
        toast.removeClass('success error').addClass(type);
        toast.fadeIn().css('display', 'block');

        setTimeout(function() {
            toast.fadeOut();
        }, 3000);
    }

    function formatDate(dateStr) {
        if (!dateStr) return '-';
        const date = new Date(dateStr);
        return date.toLocaleDateString('id-ID', {
            day: '2-digit',
            month: 'short',
            year: 'numeric'
        });
    }
});
