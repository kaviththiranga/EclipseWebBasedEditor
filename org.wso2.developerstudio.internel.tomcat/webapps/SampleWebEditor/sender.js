/**
 * Created by kavith on 1/21/15.
 */
var saveFile = function() {
    IDESaveContent(document.getElementById("editor").value);
}

var makeDirty = function() {

    IDESetDirty(true);
}

var loadFileContent = function() {

    document.getElementById("editor").value = IDEGetFileContent();
}

var onFocusGain = function() {
    getIDEDirtyContent();
}

var onFocusLost = function(){
    setIDEDirtyContent();
}

var getIDEDirtyContent = function(){
    document.getElementById("editor").value = IDEGetDirtyContent();
}

var setIDEDirtyContent = function(){
    IDESetDirtyContent(document.getElementById("editor").value);
}
