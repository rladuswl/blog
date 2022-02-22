let index = {
    init: function () {
        $("#btn-save").on("click", ()=>{
            this.save();
        });
        $("#btn-delete").on("click", ()=>{
            this.deleteById();
        });
    },

    save: function () {
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            // 결과가 정상이면 done 실행
            alert("글작성이 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            // 실패하면 fail 실행
            alert("회원가입이 실패하였습니다.");
            alert(JSON.stringify(error));
        });
    },

    deleteById: function () {
        var id = $("#id").text();

        $.ajax({
            type: "DELETE",
            url: "/api/board/"+id,
            dataType: "json"
        }).done(function (resp) {
            // 결과가 정상이면 done 실행
            alert("글삭제가 완료되었습니다.");
            location.href = "/";
        }).fail(function (error) {
            // 실패하면 fail 실행
            alert("글삭제를 실패하였습니다.");
            alert(JSON.stringify(error));
        });
    },
}


index.init();

