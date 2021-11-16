import $ from 'jquery';

const onIncludeCallbacks = [];

export function addOnIncludeCallbacks(callback = (data) => {}) {
  if (!onIncludeCallbacks.includes(callback)) {
    onIncludeCallbacks.push(callback);
  }
}

function loadIncludes(parent) {
  const elements = parent ? $(parent).find('[bg-include]') : $('[bg-include]');
  elements.each((_, e) => include(e));
}

function include(element) {
  const jElem = $(element);
  const url = jElem.attr('bg-include');
  $.ajax({
    url,
    success: (data) => {
      jElem.html(data);
      jElem.removeAttr('bg-include');
      onIncludeCallbacks.forEach((cb) => cb(data));
      loadIncludes(element); // inner html recursion
    },
    error: ({ responseText }) => {
      console.error(responseText);
    }
  });
}

loadIncludes();
