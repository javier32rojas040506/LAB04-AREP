// get html elements
let boton_send = document.getElementById("btn-send");
let boton_viewdata = document.getElementById("btn-view-data");
let input_message = document.getElementById("input_message");
let messages_list_html = document.getElementById("msg-list");
let msg = null;
let msg_list_data = [];

boton_send.onclick = () => {
    msg = input_message.value;

    // //get messages list from servers line 19
    msg_list_data.push(msg);

    console.log(msg);
    // post message to sparks servers
};

boton_viewdata.onclick = () => {
    messages_list_html.innerHTML = "";

    //get messages list from servers
    

    msg_list_data.map( msg_ele => {
        let item_list = document.createElement("li");
        item_list.innerHTML = msg_ele;
        messages_list_html.appendChild(item_list);
    })
};
