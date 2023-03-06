/*
                          _
                         | |
   ___  ___   _ __   ___ | |_  ___
  / __|/ _ \ | '_ \ / __|| __|/ __|
 | (__| (_) || | | |\__ \| |_ \__ \
  \___|\___/ |_| |_||___/ \__||___/
*/
window.CONSTS = {
    // время (в мс) отсрочки для отображения анимации подгрузки динамики
    ANIMATION_DELAY_TIME_MS : 1500,
};

/**
 * Функция проверяет, отображается ли элемент elem на экране (т. е. виден ли он в поле зрения читателя).
 */
function is_shown(elem) {
    let bounding = elem.getBoundingClientRect();
    return (
        bounding.top >= 0 &&
        bounding.left >= 0 &&
        bounding.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
        bounding.right <= (window.innerWidth || document.documentElement.clientWidth)
    );
}

/**
 * Функция, которая при возникновении события скролла отслеживает, не находится ли в зоне видимости
 * какая-либо триггерная нода. Если триггерная нода обнаружена, она отправляется в движок книги
 * для вычисления ее содержимого.
 */
function onTriggerNodeAppeared() {
    let divs = document.getElementsByClassName('trigger_node_block');
    for (let _div of divs) {
        if (is_shown(_div)) {
            Android.onTriggerNodeAppear(_div.id);
        }
    }
}

/**
 * Функция, которая при возникновении события скролла отслеживает, не находится ли в зоне видимости
 * какая-либо переменная. Если переменная обнаружена, она отправляется в движок книги
 * для вычисления ее содержимого.
 */
function onVariableAppeared() {
    let divs = document.getElementsByClassName('variable_block');
    for (let _div of divs) {
        if (is_shown(_div) && !_div.matches('.sentToEngine')) {
            // помечаем обнаруженную переменную как отправленную в движок
            // чтобы добавить ей соответствующих стилей и не обрабатывать впредь
            _div.classList.add('sentToEngine');
            // добавляем анимированные шарики на место будущего динам. контента переменной
            createAndAttachThreeBounces(_div)
            // отправляем событие в движок для вычисления значения переменной
            Android.onVariableAppear(_div.id)
        }
    }
}

/**
 * Создать три анимированных шарика и добавить их в _div.
 */
function createAndAttachThreeBounces(_div) {
    let a1 = document.createElement('div');
    a1.className = 'bounce1';
    _div.appendChild(a1);

    let a2 = document.createElement('div');
    a2.className =' bounce2';
    _div.appendChild(a2);

    let a3 = document.createElement('div');
    a3.className = 'bounce3';
    _div.appendChild(a3);
}

/**
 * Функция обрабатывает событие загрузки страницы: просит движок подгрузить содержимое, навешивает обработчики событий и делает прочие приготовления.
 */
function onPageLoad() {
    // просим движок обработать событие
    Android.onBookOpen();
    // навешиваем обработчики на событие скролла для отлова триггеров и переменных
    window.addEventListener('scroll', onTriggerNodeAppeared);
    window.addEventListener('scroll', onVariableAppeared);
}

/**
 * Вырезать все анимационные контейнеры. Вызывается при закрытии книги на случай, чтобы анимация не сохранилась.
 */
function replaceAnimationDivs() {
    // здесь нужно найти все div.fade-in и вызвать для них replaceAnimationDiv
    let divs = document.getElementsByClassName('fade-in');
    for (let _div of divs) {
        replaceAnimationDiv(_div);
    }
}

function replaceAnimationDiv(_div) {
    // здесь нужно _div выдрать и вставить на его место его содержимое
    var elem = document.createElement("span");
    elem.innerHTML = _div.innerHTML;
    $(_div).replaceWith($(elem));
    $(elem).children(".reason-block").replaceWith("");
}

/*
  ____   _____   ______  _   _
 / __ \ |  __ \ |  ____|| \ | |
| |  | || |__) || |__   |  \| |
| |  | ||  ___/ |  __|  | . ` |
| |__| || |     | |____ | |\  |
 \____/ |_|     |______||_| \_|

 _____  _   _  _______  ______  _____   ______        _____  ______
|_   _|| \ | ||__   __||  ____||  __ \ |  ____|/\    / ____||  ____|
  | |  |  \| |   | |   | |__   | |__) || |__  /  \  | |     | |__
  | |  | . ` |   | |   |  __|  |  _  / |  __|/ /\ \ | |     |  __|
 _| |_ | |\  |   | |   | |____ | | \ \ | |  / ____ \| |____ | |____
|_____||_| \_|   |_|   |______||_|  \_\|_| /_/    \_\\_____||______|

______  _    _  _   _   _____   _____
|  ____|| |  | || \ | | / ____| / ____|
| |__   | |  | ||  \| || |     | (___
|  __|  | |  | || . ` || |      \___ \
| |     | |__| || |\  || |____  ____) |
|_|      \____/ |_| \_| \_____||_____/

*/

/**
 * Функция вставляет содержимое переменной на место маркировочной структуры.
 * Функция вызывается из движка книги и принадлежит открытому интерфейсу WebView читалки.
 */
function replaceVariableBlock(id, html) {
    setTimeout (
    function() {
        // создаем блок для содержимого переменной
        var temp_contents_block = document.createElement("div");
        temp_contents_block.setAttribute("class", "fade-in");
        temp_contents_block.innerHTML = html;
        // поиск div с переменной
        var var_block = document.getElementById(id);
        var parentDiv = var_block.parentNode;
        // замена блока с переменной на временный блок с содержимым
        parentDiv.replaceChild(temp_contents_block, var_block);
        temp_contents_block.addEventListener("animationend", function() {replaceAnimationDiv(temp_contents_block);});
    }, window.CONSTS.ANIMATION_DELAY_TIME_MS
    );
}
/**
 * Функция вставляет содержимое на место триггерной ноды.
 * Функция вызывается из движка книги и принадлежит открытому интерфейсу WebView читалки.
 */
function replaceTriggerNodeBlock(id, html) {
    // создаем блок для содержимого
    var temp_contents_block = document.createElement("div");
    temp_contents_block.setAttribute("class", "fade-in");
    temp_contents_block.innerHTML = html;
    // поиск div-а триггерной ноды
    var var_block = document.getElementById(id);
    var parentDiv = var_block.parentNode;
    // замена блока триггерной ноды на временный блок с содержимым
    parentDiv.replaceChild(temp_contents_block, var_block);
    temp_contents_block.addEventListener("animationend", function() {replaceAnimationDiv(temp_contents_block);});
}

/**
 * Функция возвращает содержимое тега BODY - то есть то, что считается текущим прогрессом чтения книги.
 * Функция вызывается из движка книги и принадлежит открытому интерфейсу WebView читалки.
 */
function getHtml() {
    // караем всю анимацию
    replaceAnimationDivs();
    // Получаем метаблок, в котором будет храниться информация о местоположении экрана
    var meta_blocks = document.getElementsByClassName('scroll-pos-meta-block');
    var meta_block = meta_blocks[0];
    // Получаем текущее местоположение экрана (top и left)
    var scrollLeft = window.pageXOffset || document.documentElement.scrollLeft,
        scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    // Добавляем в метаблок информацию о местоположении экрана
    meta_block.setAttribute('data-scroll-pos-top', scrollTop);
    meta_block.setAttribute('data-scroll-pos-left', scrollLeft);

    return document.querySelector("body").innerHTML;
}

/**
 * Функция загружает содержимое в тег BODY - то есть подгружает в браузер то, что считается текущим прогрессом чтения книги.
 * Функция вызывается из движка книги и принадлежит открытому интерфейсу WebView читалки.
 */
function loadHtml(html) {
    // загружаем контент, присланный движком
    document.querySelector("body").innerHTML = html;
    // просим на тот случай, если в начале книги есть динамика, обработать её без скролла
    onTriggerNodeAppeared();
    onVariableAppeared();
    // Получаем метаблок, в котором храниться информация о местоположении экрана
    var meta_blocks = document.getElementsByClassName('scroll-pos-meta-block');
    var meta_block = meta_blocks[0];
    // Если в данный момент данных нет, значит мы в самом начале книги
    if (meta_block.hasAttribute("data-scroll-pos-left") && meta_block.hasAttribute("data-scroll-pos-top")) {
        // Устанавливаем местоположение документа по полученным из данных метаблока
        document.documentElement.scrollTop = document.body.scrollTop = meta_block.getAttribute('data-scroll-pos-top');
        document.documentElement.scrollLeft = document.body.scrollLeft = meta_block.getAttribute('data-scroll-pos-left');
    }
}