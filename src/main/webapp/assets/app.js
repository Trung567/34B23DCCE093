document.addEventListener('DOMContentLoaded', () => {
  const printButton = document.querySelector('[data-print]');
  if (printButton) printButton.addEventListener('click', () => window.print());
  const approval = document.querySelector('[data-approval-form]');
  if (approval) approval.addEventListener('submit', () => {
    const button = approval.querySelector('button[type="submit"]');
    button.disabled = true;
    button.textContent = 'Đang duyệt và xuất hàng…';
  });
});
