function addFields(commentId){
    var container = document.getElementById("container-"+commentId);
    var addText = document.getElementById("addText-"+commentId);
    var textValue = container.innerText;

    let text1 = addText.childNodes[0];
    let text2 = addText.childNodes[1];


    while (addText.hasChildNodes()) {
        addText.removeChild(addText.lastChild);
    }

    let node = container.lastChild;

    while (container.hasChildNodes()) {
        container.removeChild(container.lastChild);
    }
    var input = document.createElement("input");
    input.type = "text";
    input.name = "editComment";
    input.style.cssText = 'width:500px;';
    input.id = "input";
    input.value = textValue;
    input.autocomplete = "off";
    container.appendChild(input);

    var edit = document.createElement("button");
    edit.type = "button";
    edit.innerText = "저장";
    edit.style.cssText = 'text-decoration: none; border: none;background: none;'
    addText.appendChild(edit);

    var back = document.createElement("button");
    back.type = "button";
    back.innerText = "취소";
    back.style.cssText = 'text-decoration: none; border: none;background: none;'
    addText.appendChild(back);

    edit.onclick = function () {
        update(commentId);
        edit.remove();
        back.remove();
        addText.appendChild(text1);
        addText.appendChild(text2);
    };
    back.onclick = function () {
        edit.remove();
        input.remove();
        back.remove();
        container.appendChild(node);
        addText.appendChild(text1);
        addText.appendChild(text2);
    };

    function update(commentId) {
        var content = $("#input").val();
        var root = "#container-"+commentId;
        var dateRoot = "#tdDate-"+commentId;
        // var date = new Date();
        $.ajax({
            url: '/comments/'+commentId,
            type: 'put',
            dataType: "json",
            data: {'content': content, 'cid': commentId},
        }).done(function (comment) {
            // $("#container-27").replaceWith(fragment);
            $(root).text(comment.comment);
            // $(dateRoot).text(comment.date);
        });
    }
}

function reply(commentId){
    var container = document.getElementById("additional-" + commentId);
    container.style.cssText = "display:absolute";
    // var input = document.createElement("input");
    // input.type = "text";
    // input.name = "editComment";
    // input.style.cssText = 'width:500px;';
    // input.id = "reply-input";
    // input.autocomplete = "off";
    // container.appendChild(input);
}

