// ;(function($){  
//     $.fn.extend({
//     	"Table":function(divId,id,row,col,jsonObject,headList,content){  
//         // var row = 16;
//         var table = $("<table class='re' id="+id+">"),
//         // 设置表头
//         setHead = function(){
//         	for(var n = 0;n<col;n++){
// 				var th = $("<th>"+headList[n]+"</th>");
// 				$(th).appendTo(table);
// 			}
// 		},
// 		// 设置内容
// 		setContent = function(){	
// 			var td=new Array();
// 			/*$.each(content, function(i, item){
// 				$.each(item,function(j,val){
// 					td.push(val);								
// 				});				
// 			});*/
// 			setContent = function(){	
// 			var td=new Array();
// 			for ( var a in jsonObject.class) {//获取有几个person
// 				var tr = document.createElement("tr");
// 				tr.setAttribute('id','tr1');
// 				$(tr).appendTo(table);
// 				$("<td>").appendTo(tr);
// 				$("tr#tr1:last").append(" <input type='checkbox' name='zhuangtai'>");
// 				$("</td>").appendTo(tr);
//                 var p = jsonObject.school[a][classId];//得到属性值的内容 
//                 $("<td>"+p+"</td>").appendTo(tr);
//                 var p = jsonObject.school[a][schoolId];//得到属性值的内容 
//                 $("<td>"+p+"</td>").appendTo(tr);
//                 var p = jsonObject.school[a][classDetail];//得到属性值的内容 
//                 $("<td>"+p+"</td>").appendTo(tr);
// 				// 设置序号
// 				for(var i =0;i<row;i++){
// 					$("#re tr:eq("+i+") td:eq(0)").text(i+1);
// 				}
				
//             }  			
// 		};

// 		table.appendTo($(divId));
		
// 		$("<thead>").appendTo(table);
// 		setHead();
// 		$("</thead>").appendTo(table);
// 		$("<tbody>").appendTo(table);
// 		setContent();
// 		$("</tbody>").appendTo(table);
// 		$(divId).append("</table>");
// 		// 样式
// 		$(".re th:eq(0),#bank th:eq(0)").css({"width":"50px","text-align":"center"});
// 		$("#re tr td:last-child()").css({"width":"50px","margin":"0 auto","vertical-align": "middle"});
// 		$(".re tr td:first-child()").css({"background":"linear-gradient(to bottom, #f9f9f9 , #f1f1f1)","text-align":"center"});
		
//     }//table结束  
// 	})//fn结束
// }(jQuery));
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

			for ( var a in jsonObject.class) {//获取有几个person
				var tr = document.createElement("tr");
				tr.setAttribute('id','tr1');
				tr.setAttribute('style','border: 1px solid #ddd');
				$(tr).appendTo(table);
				$("<td>").appendTo(tr);
				$("tr#tr1:last").append(" <input type='checkbox' name='zhuangtai'>");
				$("</td>").appendTo(tr);
                for ( var item in jsonObject.class[a]) {  
                    if(item=="classDetail") 
                    	var p1 = jsonObject.class[a][item];//得到属性值的内容 
                	if(item=="className") 
                		var p2 = jsonObject.class[a][item];//得到属性值的内容
                	if(item=="classId") 
                		var p3 = jsonObject.class[a][item];//得到属性值的内容
                }
                $("<td>"+p3+"</td>").appendTo(tr); 
                $("<td>"+p2+"</td>").appendTo(tr);
                $("<td>"+p1+"</td>").appendTo(tr);               
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
	})
}(jQuery));
  
