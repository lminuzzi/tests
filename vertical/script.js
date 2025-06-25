$(document).ready(function () {
  const maxVisible = 5;
  const $list = $('.item-list');
  const $items = $list.find('li');
  let currentIndex = 0;

  function updateVisibleItems() {
    $items.hide();
    $items.slice(currentIndex, currentIndex + maxVisible).show();
  }

  $('.arrow.up').click(function () {
    if (currentIndex > 0) {
      currentIndex--;
      updateVisibleItems();
    }
  });

  $('.arrow.down').click(function () {
    if (currentIndex + maxVisible < $items.length) {
      currentIndex++;
      updateVisibleItems();
    }
  });

  $items.click(function () {
    const direction = $(this).data('direction');
    const distance = 300;

    $('.content-area').animate({
      scrollLeft: direction === 'right'
        ? '+=' + distance
        : '-=' + distance
    }, 400);
  });

  updateVisibleItems();
});
