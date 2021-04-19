import $ from 'jquery';
import { addOnIncludeCallbacks } from '../core/includes';

const duration = 600;
let i = 0;
function filterByCity(name) {
  const debug = i++;
  $('[bg-city]').each((_, e) => {
    const isTarget = name === null || $(e).attr('bg-city') === name;
    const elem = $(e);
    if (isTarget) {
      elem.parent().removeClass('d-none');
    } else {
      elem.parent().addClass('d-none');
    }
  });
}

$.fn.cityButtons = function() {
  const cities = new Set();
  $('[bg-city]').each((_, e) => {
    cities.add($(e).attr('bg-city'));
  });

  const buildBtn = (html) =>
    $('<button>')
      .addClass(['btn', 'btn-info'])
      .html(html);
  const btns = Array.from(cities).map((city) => buildBtn(city).on('click', () => filterByCity(city)));
  const btnAll = buildBtn('Todas');
  btnAll.on('click', () => filterByCity(null));
  btns.push(btnAll);

  const btnGroup = $('<div>').addClass(['btn-group']);
  btnGroup.append(btns);
  this.html(btnGroup);
  return this;
};

addOnIncludeCallbacks(() => $('[bg-city-buttons]').cityButtons());
