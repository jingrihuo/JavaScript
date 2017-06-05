;(function($){  
    $.fn.extend({
    	"Table":function(divId,id,row,col,jsonObject,headList,content){  
        // var row = 16;
        var table = $("<table class='re' id="+id+">"),
        // 设置表头
        setHead = function(){
        	for(var n = 0;n<col;n++){
				var th = $("<th>"+headList[n]+"</th>");
				$(th).appendTo(table);
			}
		},
		// 设置内容
		setContent = function(){	
			var td=new Array();
			// $.each(content, function(i, item){
			// 	$.each(item,function(j,val){
			// 		td.push(val);								
			// 	});				
			// });
			/*方法二按需取
			var object = JSON.parse(responseText);	
			for (var i = 0; i < object.length; i++) {
				var tr = document.createElement("tr");
				tr.setAttribute('id','tr1');
				$(tr).appendTo(table);
				$("<td>").appendTo(tr);
				$("tr#tr1:last").append(" <input type='checkbox' name='zhuangtai'>");
				$("</td>").appendTo(tr);
				for (var i = 0; i < col; i++) {
					if()
                    $("<td>"+p+"</td>").appendTo(tr);
				};
				$("</tr>").appendTo(table);
				// 设置序号
				for(var i =0;i<row;i++){
					$("#re tr:eq("+i+") td:eq(0)").text(i+1);
				}
			}*/

			for ( var a in jsonObject.teacher) {//获取有几个person
				var tr = document.createElement("tr");
				tr.setAttribute('id','tr1');
				tr.setAttribute('style','border: 1px solid #ddd');
				$(tr).appendTo(table);
				$("<td>").appendTo(tr);
				$("tr#tr1:last").append(" <input type='checkbox' name='zhuangtai'>");
				$("</td>").appendTo(tr);
                for ( var item in jsonObject.teacher[a]) {  
                    if(item=="classId") 
                    	var p1 = jsonObject.teacher[a][item];//得到属性值的内容 
                	if(item=="className") 
                		var p2 = jsonObject.teacher[a][item];//得到属性值的内容
                	if(item=="teacherId") 
                		var p3 = jsonObject.teacher[a][item];//得到属性值的内容
                	if(item=="userAccount") 
                    	var p4 = jsonObject.teacher[a][item];//得到属性值的内容 
                	if(item=="teacherName") 
                		var p5 = jsonObject.teacher[a][item];//得到属性值的内容
                	if(item=="userSex") 
                		var p6 = jsonObject.teacher[a][item];//得到属性值的内容
                	if(item=="teacherType") 
                    	var p7 = jsonObject.teacher[a][item];//得到属性值的内容 
                	if(item=="project") 
                		var p8 = jsonObject.teacher[a][item];//得到属性值的内容
                	
                }
                $("<td>"+p1+"</td>").appendTo(tr); 
                $("<td>"+p2+"</td>").appendTo(tr);
                $("<td>"+p3+"</td>").appendTo(tr);    
                $("<td>"+p4+"</td>").appendTo(tr); 
                $("<td>"+p5+"</td>").appendTo(tr);
                $("<td>"+p6+"</td>").appendTo(tr); 
                $("<td>"+p7+"</td>").appendTo(tr); 
                $("<td>"+p8+"</td>").appendTo(tr);
                           
                $("</tr>").appendTo(table);;

				// 设置序号
				for(var i =0;i<row;i++){
					$("#re tr:eq("+i+") td:eq(0)").text(i+1);
				}
				
            }  
			

		};

		table.appendTo($(divId));
		
		$("<thead>").appendTo(table);
		setHead();
		$("</thead>").appendTo(table);
		$("<tbody>").appendTo(table);
		setContent();
		$("</tbody>").appendTo(table);
		$(divId).append("</table>");
		// 样式
		$(".re th:eq(0),#bank th:eq(0)").css({"width":"50px","text-align":"center"});
		$("#re tr td:last-child()").css({"width":"50px","margin":"0 auto","vertical-align": "middle"});
		$(".re tr td:first-child()").css({"background":"linear-gradient(to bottom, #f9f9f9 , #f1f1f1)","text-align":"center"});
		
    }//table结束  
	})//fn结束
}(jQuery));
  