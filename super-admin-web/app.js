/**
 * EduCloud Super Admin - Simple & Robust Dashboard Logic
 */

const API_BASE_URL = 'http://localhost:8080';
let schoolsList = [];

// DOM Elements
const statusDot = document.getElementById('status-dot');
const statusText = document.getElementById('status-text');
const statTotal = document.getElementById('stat-total');
const statPrivate = document.getElementById('stat-private');
const statPublic = document.getElementById('stat-public');
const schoolsTableBody = document.getElementById('schools-table-body');
const searchInput = document.getElementById('search-input');

// Add Modal Elements
const btnOpenAddModal = document.getElementById('btn-open-add-modal');
const addSchoolModal = document.getElementById('add-school-modal');
const btnCloseAddModal = document.getElementById('btn-close-add-modal');
const btnCancelAdd = document.getElementById('btn-cancel-add');
const formAddSchool = document.getElementById('form-add-school');
const btnSubmitSchool = document.getElementById('btn-submit-school');

// Edit Modal Elements
const editSchoolModal = document.getElementById('edit-school-modal');
const btnCloseEditModal = document.getElementById('btn-close-edit-modal');
const btnCancelEdit = document.getElementById('btn-cancel-edit');
const formEditSchool = document.getElementById('form-edit-school');
const editId = document.getElementById('edit-id');
const editName = document.getElementById('edit-name');
const editPhone = document.getElementById('edit-phone');
const editAddress = document.getElementById('edit-address');

// Tenant Test Elements
const selectTestSchool = document.getElementById('select-test-school');
const btnTestTenant = document.getElementById('btn-test-tenant');
const testResultBox = document.getElementById('test-result-box');
const testRouteText = document.getElementById('test-route-text');
const testStatusBadge = document.getElementById('test-status-badge');
const testJsonOutput = document.getElementById('test-json-output');

// ==========================================
// Initialization
// ==========================================
document.addEventListener('DOMContentLoaded', () => {
    initLucide();
    setupEventListeners();
    checkBackendConnection();
    loadSchools();
});

function initLucide() {
    if (window.lucide) {
        window.lucide.createIcons();
    }
}

// ==========================================
// Check Connection
// ==========================================
async function checkBackendConnection() {
    try {
        const res = await fetch(`${API_BASE_URL}/api/schools`);
        if (res.ok || res.status === 200 || res.status === 401 || res.status === 403) {
            statusDot.className = 'dot online';
            statusText.textContent = 'متصل بالباكيند (8080)';
        } else {
            throw new Error('Offline');
        }
    } catch {
        statusDot.className = 'dot';
        statusText.textContent = 'غير متصل بالخادم';
    }
}

// ==========================================
// Load Schools from Backend (GET /api/schools)
// ==========================================
async function loadSchools() {
    try {
        const res = await fetch(`${API_BASE_URL}/api/schools`);
        if (!res.ok) throw new Error('فشل جلب بيانات المدارس');
        const data = await res.json();
        schoolsList = Array.isArray(data) ? data : [];

        updateStats();
        renderTable();
        populateTestDropdown();
    } catch (err) {
        console.error(err);
        schoolsTableBody.innerHTML = `
            <tr>
                <td colspan="7" class="loading-row" style="color: var(--danger);">
                    <i data-lucide="alert-circle"></i> تعذر الاتصال بالباكيند. تأكد من تشغيل المشروع على Port 8080
                </td>
            </tr>
        `;
        initLucide();
    }
}

function updateStats() {
    statTotal.textContent = schoolsList.length;
    statPrivate.textContent = schoolsList.filter(s => s.schoolType === 'PRIVATE').length;
    statPublic.textContent = schoolsList.filter(s => s.schoolType === 'PUBLIC').length;
}

function renderTable() {
    const query = (searchInput.value || '').toLowerCase().trim();
    const filtered = schoolsList.filter(s => {
        return !query || 
            (s.name && s.name.toLowerCase().includes(query)) ||
            (s.code && s.code.toLowerCase().includes(query)) ||
            (s.address && s.address.toLowerCase().includes(query));
    });

    if (filtered.length === 0) {
        schoolsTableBody.innerHTML = `
            <tr>
                <td colspan="7" class="loading-row">لا توجد مدارس مسجلة تطابق البحث</td>
            </tr>
        `;
        return;
    }

    schoolsTableBody.innerHTML = filtered.map((s, idx) => `
        <tr>
            <td><strong>#${s.id || idx + 1}</strong></td>
            <td><strong>${s.name || 'بدون اسم'}</strong></td>
            <td>
                <span class="tenant-code">
                    <i data-lucide="hash"></i> ${s.code || 'N/A'}
                </span>
                <button class="btn-copy" onclick="copyCode('${s.code}')" title="نسخ الكود">
                    <i data-lucide="copy"></i>
                </button>
            </td>
            <td>
                <span class="pill ${s.schoolType === 'PRIVATE' ? 'private' : 'public'}">
                    ${s.schoolType === 'PRIVATE' ? 'خاصة' : 'حكومية'}
                </span>
            </td>
            <td>
                ${(s.educationStages || []).map(st => `<span class="pill stage">${st}</span>`).join(' ')}
            </td>
            <td>
                <div>${s.phone || '-'}</div>
                <small style="color: var(--text-muted);">${s.address || '-'}</small>
            </td>
            <td>
                <div class="table-actions">
                    <button class="btn-icon" onclick="openEditModal(${s.id})" title="تعديل">
                        <i data-lucide="edit-2"></i>
                    </button>
                    <button class="btn-icon delete" onclick="deleteSchool(${s.id})" title="حذف">
                        <i data-lucide="trash-2"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');

    initLucide();
}

function populateTestDropdown() {
    if (!selectTestSchool) return;
    selectTestSchool.innerHTML = '<option value="">-- اختر مدرسة لتجربتها --</option>' +
        schoolsList.map(s => `<option value="${s.code}">${s.name} (${s.code})</option>`).join('');
}

// ==========================================
// Event Listeners & Modals
// ==========================================
function setupEventListeners() {
    // Search
    if (searchInput) searchInput.addEventListener('input', renderTable);

    // Open/Close Add Modal
    if (btnOpenAddModal) btnOpenAddModal.addEventListener('click', () => addSchoolModal.classList.add('open'));
    if (btnCloseAddModal) btnCloseAddModal.addEventListener('click', () => addSchoolModal.classList.remove('open'));
    if (btnCancelAdd) btnCancelAdd.addEventListener('click', () => addSchoolModal.classList.remove('open'));

    // Open/Close Edit Modal
    if (btnCloseEditModal) btnCloseEditModal.addEventListener('click', () => editSchoolModal.classList.remove('open'));
    if (btnCancelEdit) btnCancelEdit.addEventListener('click', () => editSchoolModal.classList.remove('open'));

    // Submit Add School (POST /api/auth/register)
    if (formAddSchool) {
        formAddSchool.addEventListener('submit', async (e) => {
            e.preventDefault();

            const stages = Array.from(document.querySelectorAll('input[name="stage"]:checked')).map(c => c.value);
            if (stages.length === 0) {
                showToast('يرجى اختيار مرحلة تعليمية واحدة على الأقل', 'error');
                return;
            }

            const payload = {
                schoolName: document.getElementById('add-name').value.trim(),
                schoolType: document.getElementById('add-type').value,
                educationStages: stages,
                firstName: document.getElementById('add-first-name').value.trim(),
                lastName: document.getElementById('add-last-name').value.trim(),
                nationalId: document.getElementById('add-national-id').value.trim(),
                email: document.getElementById('add-email').value.trim(),
                password: document.getElementById('add-password').value.trim()
            };

            btnSubmitSchool.disabled = true;
            btnSubmitSchool.innerHTML = `<i data-lucide="loader-2" class="spin"></i> جاري الحفظ...`;
            initLucide();

            try {
                const res = await fetch(`${API_BASE_URL}/api/auth/register`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });

                if (!res.ok) {
                    const err = await res.json().catch(() => null);
                    throw new Error(err?.message || 'فشل تسجيل المدرسة');
                }

                showToast('تم تسجيل المدرسة والمدير بنجاح في السحابة! 🎉', 'success');
                formAddSchool.reset();
                addSchoolModal.classList.remove('open');
                loadSchools();
            } catch (err) {
                showToast(err.message, 'error');
            } finally {
                btnSubmitSchool.disabled = false;
                btnSubmitSchool.innerHTML = `<i data-lucide="check"></i> <span>حفظ وتسجيل المدرسة</span>`;
                initLucide();
            }
        });
    }

    // Submit Edit School (PUT /api/schools/{id})
    if (formEditSchool) {
        formEditSchool.addEventListener('submit', async (e) => {
            e.preventDefault();
            const id = editId.value;
            const payload = {
                phone: editPhone.value.trim(),
                address: editAddress.value.trim(),
                logoPath: ''
            };

            try {
                const res = await fetch(`${API_BASE_URL}/api/schools/${id}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(payload)
                });

                if (!res.ok) throw new Error('فشل تحديث البيانات');

                showToast('تم تحديث بيانات المدرسة بنجاح', 'success');
                editSchoolModal.classList.remove('open');
                loadSchools();
            } catch (err) {
                showToast(err.message, 'error');
            }
        });
    }

    // Test Tenant API (GET /api/{schoolCode}/principal/me or /api/schools/code/{code})
    if (btnTestTenant) {
        btnTestTenant.addEventListener('click', async () => {
            const code = selectTestSchool.value;
            if (!code) {
                showToast('يرجى اختيار مدرسة أولاً', 'error');
                return;
            }

            testResultBox.style.display = 'block';
            testRouteText.textContent = `Route: /api/schools/code/${code}`;
            testStatusBadge.textContent = 'Checking...';
            testJsonOutput.textContent = '// Sending request...';

            try {
                const res = await fetch(`${API_BASE_URL}/api/schools/code/${code}`);
                const data = await res.json();
                testStatusBadge.textContent = `${res.status} OK`;
                testStatusBadge.style.background = '#065f46';
                testJsonOutput.textContent = JSON.stringify(data, null, 2);
            } catch (err) {
                testStatusBadge.textContent = 'Error';
                testStatusBadge.style.background = '#991b1b';
                testJsonOutput.textContent = `Error: ${err.message}`;
            }
        });
    }
}

// ==========================================
// Edit & Delete Actions
// ==========================================
window.openEditModal = function(id) {
    const school = schoolsList.find(s => s.id === id);
    if (!school) return;

    editId.value = school.id;
    editName.value = school.name || '';
    editPhone.value = school.phone || '';
    editAddress.value = school.address || '';

    editSchoolModal.classList.add('open');
};

window.deleteSchool = async function(id) {
    const school = schoolsList.find(s => s.id === id);
    if (!confirm(`هل أنت متأكد من حذف المدرسة "${school?.name || id}"؟`)) return;

    try {
        const res = await fetch(`${API_BASE_URL}/api/schools/${id}`, { method: 'DELETE' });
        if (!res.ok) throw new Error('فشل حذف المدرسة');

        showToast('تم حذف المدرسة بنجاح', 'success');
        loadSchools();
    } catch (err) {
        showToast(err.message, 'error');
    }
};

window.copyCode = function(code) {
    if (!code || code === 'N/A') return;
    navigator.clipboard.writeText(code).then(() => {
        showToast(`تم نسخ الكود: "${code}"`, 'success');
    });
};

function showToast(msg, type = 'success') {
    const box = document.getElementById('toast-box');
    if (!box) return;

    const toast = document.createElement('div');
    toast.className = `toast-msg ${type}`;
    toast.textContent = msg;

    box.appendChild(toast);
    setTimeout(() => toast.remove(), 3500);
}
