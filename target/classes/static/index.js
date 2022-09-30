// API configuration wit RoundRobi algorithm.
const ports = [35001, 35002, 35003];
URL_API = `http://${window.location.hostname}:${ports[0]}`;
// get html elements
let boton_send = document.getElementById("btn-send");
let boton_viewdata = document.getElementById("btn-view-data");
let input_message = document.getElementById("input_message");
let messages_list_html = document.getElementById("msg-list");
let msg = null;
let msg_list_data = [];

boton_send.onclick = async() => {
    msg = input_message.value;
    const request = {
        method: 'POST',
        mode: 'cors',
        Headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            data: msg
        })
    };
    const response = await fetch(`${URL_API}/api/messages`, request);
    const receivedData = await response.text();
    console.log(receivedData);

    //get messages list from servers line 19
    msg_list_data.push(msg);
    console.log(msg);
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
