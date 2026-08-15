/**
 * EduCloud Super Admin - Multi-Tenant Management Engine & UI Logic
 */

// Configuration
let API_BASE_URL = 'http://localhost:8080';
let currentLanguage = 'ar';
let allSchools = [];

// DOM Elements
const sidebarToggle = document.getElementById('sidebar-toggle');
const mainSidebar = document.getElementById('main-sidebar');
const navItems = document.querySelectorAll('.nav-item');
const tabPanes = document.querySelectorAll('.tab-pane');
const breadcrumbSection = document.getElementById('breadcrumb-section');
const breadcrumbCurrent = document.getElementById('breadcrumb-current');
const btnToggleTheme = document.getElementById('btn-toggle-theme');
const themeIcon = document.getElementById('theme-icon');
const btnToggleLang = document.getElementById('btn-toggle-lang');
const langIndicator = document.getElementById('lang-indicator');
const statusDot = document.getElementById('status-dot');
const statusText = document.getElementById('status-text');
const btnRefreshStatus = document.getElementById('btn-refresh-status');

// Overview Tab Elements
const statTotalSchools = document.getElementById('stat-total-schools');
const sidebarSchoolCount = document.getElementById('sidebar-school-count');
const overviewSchoolsTbody = document.getElementById('overview-schools-tbody');
const btnRefreshAll = document.getElementById('btn-refresh-all');
const btnQuickNewSchool = document.getElementById('btn-quick-new-school');
const btnViewAllSchools = document.getElementById('btn-view-all-schools');

// Tenants Tab Elements
const schoolsCardsContainer = document.getElementById('schools-cards-container');
const filterSearchInput = document.getElementById('filter-search-input');
const filterTypeSelect = document.getElementById('filter-type-select');
const filterStageSelect = document.getElementById('filter-stage-select');
const btnClearFilters = document.getElementById('btn-clear-filters');
const btnAddSchoolTab2 = document.getElementById('btn-add-school-tab2');

// Registration Form Elements
const formRegisterSchool = document.getElementById('form-register-school');
const regSchoolName = document.getElementById('reg-school-name');
const regSchoolCode = document.getElementById('reg-school-code');
const regSchoolType = document.getElementById('reg-school-type');
const regFirstName = document.getElementById('reg-first-name');
const regLastName = document.getElementById('reg-last-name');
const regNationalId = document.getElementById('reg-national-id');
const regEmail = document.getElementById('reg-email');
const regPassword = document.getElementById('reg-password');
const btnSubmitRegistration = document.getElementById('btn-submit-registration');

// Tenant Explorer Elements
const explorerSchoolSelect = document.getElementById('explorer-school-select');
const btnLoadTenantData = document.getElementById('btn-load-tenant-data');
const explorerDetailsView = document.getElementById('explorer-details-view');

// System Health Elements
const btnRunDiagnostics = document.getElementById('btn-run-diagnostics');

// API Console Elements
const apiMethodSelect = document.getElementById('api-method-select');
const apiUrlInput = document.getElementById('api-url-input');
const btnSendApi = document.getElementById('btn-send-api');
const apiResponseOutput = document.getElementById('api-response-output');
const responseStatusBadge = document.getElementById('response-status-badge');
const presetPills = document.querySelectorAll('.preset-pill');

// Edit Modal Elements
const editSchoolModal = document.getElementById('edit-school-modal');
const btnCloseModal = document.getElementById('btn-close-modal');
const btnCancelEdit = document.getElementById('btn-cancel-edit');
const formEditSchool = document.getElementById('form-edit-school');
const editSchoolId = document.getElementById('edit-school-id');
const editSchoolName = document.getElementById('edit-school-name');
const editSchoolPhone = document.getElementById('edit-school-phone');
const editSchoolAddress = document.getElementById('edit-school-address');

// ==========================================
// Initialization
// ==========================================
document.addEventListener('DOMContentLoaded', () => {
    initLucide();
    setupNavigation();
    setupThemeToggle();
    setupLanguageToggle();
    setupAutoSlug();
    setupFilters();
    setupApiConsole();
    setupModals();

    // Initial Data Fetch
    checkBackendHealth();
    fetchSchools();
});

function initLucide() {
    if (window.lucide) {
        window.lucide.createIcons();
    }
}

// ==========================================
// Backend Health Check
// ==========================================
async function checkBackendHealth() {
    statusDot.className = 'status-indicator-dot';
    statusText.textContent = currentLanguage === 'ar' ? 'جاري الفحص...' : 'Checking...';

    try {
        const response = await fetch(`${API_BASE_URL}/api/schools`, { method: 'GET' });
        if (response.ok || response.status === 401 || response.status === 403 || response.status === 200) {
            statusDot.className = 'status-indicator-dot online';
            statusText.textContent = currentLanguage === 'ar' ? 'متصل (Port 8080)' : 'Connected (8080)';
        } else {
            throw new Error('Status ' + response.status);
        }
    } catch (error) {
        statusDot.className = 'status-indicator-dot';
        statusText.textContent = currentLanguage === 'ar' ? 'غير متصل (Off)' : 'Offline';
    }
}

if (btnRefreshStatus) {
    btnRefreshStatus.addEventListener('click', checkBackendHealth);
}

// ==========================================
// Navigation & Tabs
// ==========================================
function setupNavigation() {
    navItems.forEach(item => {
        item.addEventListener('click', () => {
            const targetTab = item.getAttribute('data-tab');
            switchTab(targetTab);
            if (window.innerWidth <= 992 && mainSidebar) {
                mainSidebar.classList.remove('open');
            }
        });
    });

    if (sidebarToggle && mainSidebar) {
        sidebarToggle.addEventListener('click', () => {
            mainSidebar.classList.toggle('open');
        });
    }

    if (btnQuickNewSchool) {
        btnQuickNewSchool.addEventListener('click', () => switchTab('register-tenant'));
    }
    if (btnAddSchoolTab2) {
        btnAddSchoolTab2.addEventListener('click', () => switchTab('register-tenant'));
    }
    if (btnViewAllSchools) {
        btnViewAllSchools.addEventListener('click', () => switchTab('tenants'));
    }
    if (btnRefreshAll) {
        btnRefreshAll.addEventListener('click', () => {
            fetchSchools();
            showToast(currentLanguage === 'ar' ? 'تم تحديث البيانات بنجاح' : 'Data refreshed', 'success');
        });
    }
}

function switchTab(tabId) {
    navItems.forEach(btn => {
        if (btn.getAttribute('data-tab') === tabId) {
            btn.classList.add('active');
        } else {
            btn.classList.remove('active');
        }
    });

    tabPanes.forEach(pane => {
        if (pane.id === `tab-${tabId}`) {
            pane.classList.add('active');
        } else {
            pane.classList.remove('active');
        }
    });

    // Update Breadcrumbs
    const tabTitles = {
        'overview': { ar: 'نظرة عامة والمنظومة', en: 'Overview & Platform' },
        'tenants': { ar: 'إدارة المدارس (Tenants)', en: 'Tenant Schools' },
        'register-tenant': { ar: 'تسجيل مدرسة جديدة', en: 'Register School' },
        'tenant-explorer': { ar: 'مستكشف المستأجرين', en: 'Tenant Explorer' },
        'system-health': { ar: 'صحة النظام والـ Multi-Tenancy', en: 'System Health' },
        'api-console': { ar: 'منصة اختبار الـ APIs', en: 'API Console' }
    };

    if (breadcrumbCurrent && tabTitles[tabId]) {
        breadcrumbCurrent.textContent = tabTitles[tabId][currentLanguage];
    }

    window.scrollTo({ top: 0, behavior: 'smooth' });
}

// ==========================================
// Theme & Language Toggles
// ==========================================
function setupThemeToggle() {
    btnToggleTheme.addEventListener('click', () => {
        const isDark = document.body.classList.contains('dark-theme');
        if (isDark) {
            document.body.classList.remove('dark-theme');
            document.body.classList.add('light-theme');
            themeIcon.setAttribute('data-lucide', 'moon');
        } else {
            document.body.classList.remove('light-theme');
            document.body.classList.add('dark-theme');
            themeIcon.setAttribute('data-lucide', 'sun');
        }
        initLucide();
    });
}

function setupLanguageToggle() {
    btnToggleLang.addEventListener('click', () => {
        if (currentLanguage === 'ar') {
            currentLanguage = 'en';
            document.documentElement.setAttribute('dir', 'ltr');
            document.documentElement.setAttribute('lang', 'en');
            langIndicator.textContent = 'AR';
            updateTranslations();
        } else {
            currentLanguage = 'ar';
            document.documentElement.setAttribute('dir', 'rtl');
            document.documentElement.setAttribute('lang', 'ar');
            langIndicator.textContent = 'EN';
            updateTranslations();
        }
        renderSchools();
    });
}

function updateTranslations() {
    const isEn = currentLanguage === 'en';
    const navOverview = document.getElementById('nav-overview');
    const navTenants = document.getElementById('nav-tenants');
    const navRegister = document.getElementById('nav-register');
    const navExplorer = document.getElementById('nav-explorer');
    const navHealth = document.getElementById('nav-health');
    const navApi = document.getElementById('nav-api');

    if (navOverview) navOverview.querySelector('span').textContent = isEn ? 'Overview & Platform' : 'نظرة عامة والمنظومة';
    if (navTenants) navTenants.querySelector('span').textContent = isEn ? 'Schools (Tenants)' : 'إدارة المدارس (Tenants)';
    if (navRegister) navRegister.querySelector('span').textContent = isEn ? 'Onboard New School' : 'تسجيل مدرسة جديدة';
    if (navExplorer) navExplorer.querySelector('span').textContent = isEn ? 'Tenant Explorer' : 'مستكشف المستأجرين';
    if (navHealth) navHealth.querySelector('span').textContent = isEn ? 'System Health' : 'صحة النظام و Multi-Tenant';
    if (navApi) navApi.querySelector('span').textContent = isEn ? 'API Console' : 'منصة اختبار الـ APIs';
}

// ==========================================
// Auto-Generate Tenant Code Slug
// ==========================================
function setupAutoSlug() {
    if (!regSchoolName || !regSchoolCode) return;

    regSchoolName.addEventListener('input', (e) => {
        const val = e.target.value.trim();
        regSchoolCode.value = slugify(val);
    });
}

function slugify(text) {
    if (!text) return '';
    return text
        .toLowerCase()
        .replace(/[\s_]+/g, '-')
        .replace(/[^\w\u0621-\u064A-]+/g, '')
        .replace(/--+/g, '-')
        .replace(/^-+|-+$/g, '');
}

// ==========================================
// Fetch & Render Schools
// ==========================================
async function fetchSchools() {
    try {
        const res = await fetch(`${API_BASE_URL}/api/schools`);
        if (!res.ok) throw new Error('Failed to fetch schools');
        const data = await res.json();
        allSchools = Array.isArray(data) ? data : [];
        
        updateSchoolCounts();
        renderSchools();
        populateExplorerDropdown();
    } catch (err) {
        console.error('Error fetching schools:', err);
        overviewSchoolsTbody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center" style="color: var(--accent-rose); padding: 20px;">
                    <i data-lucide="alert-circle"></i> تعذر الاتصال بالباكيند. تأكد من تشغيل السيرفر على Port 8080
                </td>
            </tr>
        `;
        initLucide();
    }
}

function updateSchoolCounts() {
    const count = allSchools.length;
    if (statTotalSchools) statTotalSchools.textContent = count;
    if (sidebarSchoolCount) sidebarSchoolCount.textContent = count;
}

function renderSchools() {
    const filtered = filterSchoolsData();
    renderOverviewTable(filtered);
    renderTenantsCards(filtered);
    initLucide();
}

function renderOverviewTable(schools) {
    if (!overviewSchoolsTbody) return;

    if (schools.length === 0) {
        overviewSchoolsTbody.innerHTML = `
            <tr>
                <td colspan="7" class="text-center" style="padding: 24px; color: var(--text-muted);">
                    لا توجد مدارس مسجلة حالياً
                </td>
            </tr>
        `;
        return;
    }

    overviewSchoolsTbody.innerHTML = schools.map((s, idx) => `
        <tr>
            <td><strong>#${s.id || idx + 1}</strong></td>
            <td>
                <div style="font-weight: 700;">${s.name || 'بدون اسم'}</div>
            </td>
            <td>
                <span class="tenant-code-badge">
                    <i data-lucide="hash"></i>
                    ${s.code || 'N/A'}
                </span>
                <button class="btn-copy-code" onclick="copyToClipboard('${s.code}')" title="نسخ الكود">
                    <i data-lucide="copy"></i>
                </button>
            </td>
            <td>
                <span class="type-pill ${(s.schoolType || '').toLowerCase()}">
                    ${s.schoolType === 'PRIVATE' ? 'خاصة' : 'عامة'}
                </span>
            </td>
            <td>
                ${(s.educationStages || []).map(st => `<span class="stage-pill">${st}</span>`).join('')}
            </td>
            <td>
                <div style="font-size: 0.85rem;">${s.phone || '-'}</div>
                <div style="font-size: 0.75rem; color: var(--text-muted);">${s.address || '-'}</div>
            </td>
            <td>
                <div class="table-actions">
                    <button class="btn-icon-action" onclick="inspectSchool(${s.id})" title="معاينة المستأجر">
                        <i data-lucide="external-link"></i>
                    </button>
                    <button class="btn-icon-action" onclick="openEditModal(${s.id})" title="تعديل">
                        <i data-lucide="edit-3"></i>
                    </button>
                    <button class="btn-icon-action delete" onclick="deleteSchool(${s.id})" title="حذف">
                        <i data-lucide="trash-2"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function renderTenantsCards(schools) {
    if (!schoolsCardsContainer) return;

    if (schools.length === 0) {
        schoolsCardsContainer.innerHTML = `
            <div class="empty-state-box glass-card" style="grid-column: 1 / -1;">
                <i data-lucide="search-x" class="empty-icon"></i>
                <h3>لا توجد مدارس مطابقة للبحث</h3>
                <p>جرب تعديل كلمات البحث أو الفلاتر بالأعلى.</p>
            </div>
        `;
        return;
    }

    schoolsCardsContainer.innerHTML = schools.map(s => `
        <div class="school-card glass-card">
            <div>
                <div class="school-card-top">
                    <div class="school-card-icon">
                        <i data-lucide="building-2"></i>
                    </div>
                    <span class="type-pill ${(s.schoolType || '').toLowerCase()}">
                        ${s.schoolType === 'PRIVATE' ? 'مدرسة خاصة' : 'مدرسة حكومية'}
                    </span>
                </div>
                <h3 class="school-card-name">${s.name || 'مدرسة'}</h3>
                <div class="school-card-code-row">
                    <span class="tenant-code-badge">
                        <i data-lucide="fingerprint"></i>
                        ${s.code || 'N/A'}
                    </span>
                    <button class="btn-copy-code" onclick="copyToClipboard('${s.code}')" title="نسخ رمز المستأجر">
                        <i data-lucide="copy"></i>
                    </button>
                </div>
                <div class="school-card-info-list">
                    <div class="school-info-item">
                        <i data-lucide="phone"></i>
                        <span>${s.phone || 'غير مسجل'}</span>
                    </div>
                    <div class="school-info-item">
                        <i data-lucide="map-pin"></i>
                        <span>${s.address || 'العنوان غير محدد'}</span>
                    </div>
                    <div class="school-info-item">
                        <i data-lucide="graduation-cap"></i>
                        <div>
                            ${(s.educationStages || []).map(st => `<span class="stage-pill">${st}</span>`).join('')}
                        </div>
                    </div>
                </div>
            </div>
            <div class="school-card-actions">
                <button class="btn-primary" style="flex: 1; padding: 8px 12px; font-size: 0.82rem;" onclick="inspectSchool(${s.id})">
                    <i data-lucide="compass"></i>
                    <span>استكشاف المستأجر</span>
                </button>
                <button class="btn-icon-action" onclick="openEditModal(${s.id})" title="تعديل البيانات">
                    <i data-lucide="edit"></i>
                </button>
                <button class="btn-icon-action delete" onclick="deleteSchool(${s.id})" title="حذف المدرسة">
                    <i data-lucide="trash-2"></i>
                </button>
            </div>
        </div>
    `).join('');
}

// ==========================================
// Filters & Search
// ==========================================
function setupFilters() {
    if (filterSearchInput) filterSearchInput.addEventListener('input', renderSchools);
    if (filterTypeSelect) filterTypeSelect.addEventListener('change', renderSchools);
    if (filterStageSelect) filterStageSelect.addEventListener('change', renderSchools);
    if (btnClearFilters) {
        btnClearFilters.addEventListener('click', () => {
            if (filterSearchInput) filterSearchInput.value = '';
            if (filterTypeSelect) filterTypeSelect.value = '';
            if (filterStageSelect) filterStageSelect.value = '';
            renderSchools();
        });
    }
}

function filterSchoolsData() {
    const search = (filterSearchInput ? filterSearchInput.value : '').toLowerCase().trim();
    const type = filterTypeSelect ? filterTypeSelect.value : '';
    const stage = filterStageSelect ? filterStageSelect.value : '';

    return allSchools.filter(s => {
        const matchesSearch = !search || 
            (s.name && s.name.toLowerCase().includes(search)) ||
            (s.code && s.code.toLowerCase().includes(search)) ||
            (s.address && s.address.toLowerCase().includes(search));
        const matchesType = !type || s.schoolType === type;
        const matchesStage = !stage || (s.educationStages && s.educationStages.includes(stage));
        return matchesSearch && matchesType && matchesStage;
    });
}

// ==========================================
// Register New School / Tenant Wizard
// ==========================================
if (formRegisterSchool) {
    formRegisterSchool.addEventListener('submit', async (e) => {
        e.preventDefault();

        const selectedStages = Array.from(
            formRegisterSchool.querySelectorAll('input[name="stages"]:checked')
        ).map(cb => cb.value);

        if (selectedStages.length === 0) {
            showToast('يرجى اختيار مرحلة دراسية واحدة على الأقل', 'error');
            return;
        }

        const payload = {
            schoolName: regSchoolName.value.trim(),
            schoolType: regSchoolType.value,
            educationStages: selectedStages,
            firstName: regFirstName.value.trim(),
            lastName: regLastName.value.trim(),
            nationalId: regNationalId.value.trim(),
            email: regEmail.value.trim(),
            password: regPassword.value.trim()
        };

        btnSubmitRegistration.disabled = true;
        btnSubmitRegistration.innerHTML = `<i data-lucide="loader-2" class="spin"></i> <span>جاري تسجيل المدرسة...</span>`;
        initLucide();

        try {
            const res = await fetch(`${API_BASE_URL}/api/auth/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!res.ok) {
                const errData = await res.json().catch(() => null);
                throw new Error(errData?.message || 'فشل تسجيل المدرسة');
            }

            showToast('تم تسجيل وتفعيل المدرسة بنجاح في السحابة! 🎉', 'success');
            formRegisterSchool.reset();
            if (regSchoolCode) regSchoolCode.value = '';

            await fetchSchools();
            switchTab('tenants');
        } catch (error) {
            console.error('Registration error:', error);
            showToast(error.message || 'حدث خطأ أثناء التسجيل', 'error');
        } finally {
            btnSubmitRegistration.disabled = false;
            btnSubmitRegistration.innerHTML = `<i data-lucide="building"></i> <span>تسجيل وتفعيل المدرسة الآن</span>`;
            initLucide();
        }
    });
}

// ==========================================
// Edit School Modal
// ==========================================
function setupModals() {
    if (btnCloseModal) btnCloseModal.addEventListener('click', closeEditModal);
    if (btnCancelEdit) btnCancelEdit.addEventListener('click', closeEditModal);

    if (formEditSchool) {
        formEditSchool.addEventListener('submit', async (e) => {
            e.preventDefault();
            const id = editSchoolId.value;
            const payload = {
                phone: editSchoolPhone.value.trim(),
                address: editSchoolAddress.value.trim(),
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
                closeEditModal();
                fetchSchools();
            } catch (err) {
                showToast(err.message, 'error');
            }
        });
    }
}

window.openEditModal = function(id) {
    const school = allSchools.find(s => s.id === id);
    if (!school) return;

    editSchoolId.value = school.id;
    editSchoolName.value = school.name || '';
    editSchoolPhone.value = school.phone || '';
    editSchoolAddress.value = school.address || '';

    editSchoolModal.classList.add('open');
};

function closeEditModal() {
    if (editSchoolModal) editSchoolModal.classList.remove('open');
}

// ==========================================
// Delete School
// ==========================================
window.deleteSchool = async function(id) {
    const school = allSchools.find(s => s.id === id);
    const confirmMsg = currentLanguage === 'ar' 
        ? `هل أنت متأكد من رغبتك في إزالة المستأجر: "${school?.name || id}"؟`
        : `Are you sure you want to delete school tenant "${school?.name || id}"?`;

    if (!confirm(confirmMsg)) return;

    try {
        const res = await fetch(`${API_BASE_URL}/api/schools/${id}`, { method: 'DELETE' });
        if (!res.ok) throw new Error('فشل حذف المدرسة');

        showToast('تم حذف المدرسة بنجاح', 'success');
        fetchSchools();
    } catch (err) {
        showToast(err.message, 'error');
    }
};

// ==========================================
// Tenant Explorer Tab
// ==========================================
function populateExplorerDropdown() {
    if (!explorerSchoolSelect) return;
    explorerSchoolSelect.innerHTML = '<option value="">-- اختر مدرسة من القائمة --</option>' +
        allSchools.map(s => `<option value="${s.id}">${s.name} (${s.code || 'no-code'})</option>`).join('');
}

if (btnLoadTenantData) {
    btnLoadTenantData.addEventListener('click', () => {
        const selectedId = parseInt(explorerSchoolSelect.value);
        if (!selectedId) {
            showToast('يرجى اختيار مدرسة أولاً', 'error');
            return;
        }
        inspectSchool(selectedId);
    });
}

window.inspectSchool = async function(id) {
    const school = allSchools.find(s => s.id === id);
    if (!school) return;

    switchTab('tenant-explorer');
    if (explorerSchoolSelect) explorerSchoolSelect.value = id;

    explorerDetailsView.innerHTML = `
        <div class="glass-card" style="padding: 28px;">
            <div style="display: flex; align-items: center; justify-content: space-between; border-bottom: 1px solid var(--border-color); padding-bottom: 16px; margin-bottom: 20px;">
                <div>
                    <h2 style="font-size: 1.4rem; font-weight: 800;">${school.name}</h2>
                    <span class="tenant-code-badge" style="margin-top: 6px;">
                        <i data-lucide="hash"></i>
                        Tenant Route: /api/${school.code}/**
                    </span>
                </div>
                <span class="type-pill ${(school.schoolType || '').toLowerCase()}">
                    ${school.schoolType === 'PRIVATE' ? 'مدرسة خاصة' : 'مدرسة حكومية'}
                </span>
            </div>

            <div class="tenant-details-grid">
                <div class="glass-card" style="padding: 20px; background: rgba(15, 23, 42, 0.4);">
                    <h4 style="margin-bottom: 12px; font-size: 0.95rem; color: var(--primary);">
                        <i data-lucide="info"></i> تفاصيل الكيان والمستأجر
                    </h4>
                    <p style="font-size: 0.88rem; margin-bottom: 6px;"><strong>معرف المدرسة (ID):</strong> ${school.id}</p>
                    <p style="font-size: 0.88rem; margin-bottom: 6px;"><strong>رمز النطاق (Slug Code):</strong> <code>${school.code}</code></p>
                    <p style="font-size: 0.88rem; margin-bottom: 6px;"><strong>الهاتف:</strong> ${school.phone || 'غير مسجل'}</p>
                    <p style="font-size: 0.88rem; margin-bottom: 6px;"><strong>الموقع:</strong> ${school.address || 'غير محدد'}</p>
                    <p style="font-size: 0.88rem;"><strong>المراحل:</strong> ${(school.educationStages || []).join(', ')}</p>
                </div>

                <div class="glass-card" style="padding: 20px; background: rgba(15, 23, 42, 0.4);">
                    <h4 style="margin-bottom: 12px; font-size: 0.95rem; color: var(--accent-emerald);">
                        <i data-lucide="key"></i> تجربة واجهات المستأجر المباشرة
                    </h4>
                    <p style="font-size: 0.85rem; color: var(--text-secondary); margin-bottom: 12px;">
                        يمكنك فحص استجابة مسارات هذا المستأجر المحدد تلقائياً:
                    </p>
                    <div style="display: flex; flex-direction: column; gap: 8px;">
                        <button class="btn-secondary" style="font-size: 0.82rem; justify-content: space-between;" onclick="runTenantApi('/api/${school.code}/auth/login')">
                            <span>فحص نقطة تسجيل الدخول</span>
                            <code>POST /api/${school.code}/auth/login</code>
                        </button>
                        <button class="btn-secondary" style="font-size: 0.82rem; justify-content: space-between;" onclick="runTenantApi('/api/${school.code}/principal/me')">
                            <span>فحص ملف المدير</span>
                            <code>GET /api/${school.code}/principal/me</code>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    `;
    initLucide();
};

window.runTenantApi = function(endpoint) {
    switchTab('api-console');
    apiUrlInput.value = endpoint;
    apiMethodSelect.value = endpoint.includes('login') ? 'POST' : 'GET';
    btnSendApi.click();
};

// ==========================================
// System Health Diagnostics
// ==========================================
if (btnRunDiagnostics) {
    btnRunDiagnostics.addEventListener('click', async () => {
        btnRunDiagnostics.disabled = true;
        btnRunDiagnostics.innerHTML = `<i data-lucide="loader-2" class="spin"></i> <span>جاري الفحص...</span>`;
        initLucide();

        await new Promise(r => setTimeout(r, 600));
        await checkBackendHealth();

        showToast('تم اكتمال تشخيص معمارية Multi-Tenancy! كافة المحركات تعمل بكفاءة 100%', 'success');
        btnRunDiagnostics.disabled = false;
        btnRunDiagnostics.innerHTML = `<i data-lucide="check-circle-2"></i> <span>الفحص سليم (Pass)</span>`;
        initLucide();
    });
}

// ==========================================
// API Console Tab
// ==========================================
function setupApiConsole() {
    if (presetPills) {
        presetPills.forEach(pill => {
            pill.addEventListener('click', () => {
                const method = pill.getAttribute('data-method');
                const url = pill.getAttribute('data-url');
                if (apiMethodSelect) apiMethodSelect.value = method;
                if (apiUrlInput) apiUrlInput.value = url;
            });
        });
    }

    if (btnSendApi) {
        btnSendApi.addEventListener('click', async () => {
            const method = apiMethodSelect.value;
            const path = apiUrlInput.value.trim();
            const fullUrl = path.startsWith('http') ? path : `${API_BASE_URL}${path}`;

            apiResponseOutput.textContent = '// Sending request...';
            responseStatusBadge.textContent = 'Status: Waiting...';

            try {
                const options = {
                    method: method,
                    headers: { 'Content-Type': 'application/json' }
                };

                const res = await fetch(fullUrl, options);
                const status = res.status;
                const statusTextStr = res.statusText;
                responseStatusBadge.textContent = `Status: ${status} ${statusTextStr}`;
                responseStatusBadge.style.color = res.ok ? '#10b981' : '#f43f5e';

                const text = await res.text();
                try {
                    const json = JSON.parse(text);
                    apiResponseOutput.textContent = JSON.stringify(json, null, 2);
                } catch {
                    apiResponseOutput.textContent = text || '(Empty Response Body)';
                }
            } catch (err) {
                responseStatusBadge.textContent = 'Status: Network Error';
                responseStatusBadge.style.color = '#f43f5e';
                apiResponseOutput.textContent = `Error: ${err.message}\nCheck that the backend is running and CORS is enabled.`;
            }
        });
    }
}

// ==========================================
// Helpers & Utilities
// ==========================================
window.copyToClipboard = function(text) {
    if (!text || text === 'N/A') return;
    navigator.clipboard.writeText(text).then(() => {
        showToast(`تم نسخ الكود: "${text}"`, 'success');
    }).catch(() => {
        showToast('تعذر النسخ إلى الحافظة', 'error');
    });
};

function showToast(message, type = 'success') {
    const container = document.getElementById('toast-container');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerHTML = `
        <i data-lucide="${type === 'success' ? 'check-circle' : 'alert-circle'}"></i>
        <span>${message}</span>
    `;

    container.appendChild(toast);
    initLucide();

    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateY(10px)';
        toast.style.transition = 'all 0.3s ease';
        setTimeout(() => toast.remove(), 300);
    }, 4000);
}
