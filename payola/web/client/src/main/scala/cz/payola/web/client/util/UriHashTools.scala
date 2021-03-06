package cz.payola.web.client.util

import s2js.compiler.javascript

object UriHashTools
{
    @javascript("""
          if (window.location.hash){
            var params = self.getUriHash().split('&');
            for(i = 0; i < params.length; ++i) {
                if(params[i].split('=')[0] == name) {
                    return params[i].split('=')[1]
                }
            }
          }
          return '';
                """)
    def getUriParameter(name: String) : String = null

    @javascript("""
          var currentHash = self.getCleanUriHash();
          var resultHash = ""

          var params = currentHash.split('&');
          for(i = 0; i < params.length; ++i) {
            if(params[i].split('=')[0] != "" && params[i].split('=')[0] != name ) {
                if(resultHash.length != 0) {
                  resultHash = resultHash + '&';
                }
                resultHash = resultHash + params[i]
            }
          }
          $.bbq.pushState((resultHash + '&' + name + "=" + value));
                """)
    def setUriParameter(name: String, value: String) {}

    @javascript("""
          var splitted = self.getUriHash().split('&');
          var resultHash = "";
          for(i = 0; i < splitted.length; ++i) {
            if(splitted[i].contains("=")) {
                if(resultHash.length != 0) {
                    resultHash = resultHash + '&';
                }
                resultHash = resultHash + splitted[i]
            }
          }
          if(resultHash == "") {
             $.bbq.removeState(); //in case that the hash contains only nonparamtized values, remove it
          }
          return resultHash;
                """)
    private def getCleanUriHash(): String = null

    @javascript("""
          return window.location.hash && window.location.hash.contains("=");
        """)
    def isAnyParameterInUri(): Boolean = false

    @javascript("""return window.location.hash.substr(1);""")
    def getUriHash(): String = null

    @javascript("""return encodeURIComponent(uri)""")
    def encodeURIComponent(uri: String) : String = ""

    @javascript("""return decodeURIComponent(uri)""")
    def decodeURIComponent(uri: String) : String = ""


}
