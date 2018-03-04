# MapList

`Groovy` 用のライブラリ。  
`Spock` のパラメータの定義のような書式で、カラム名がMapのキーとなったリストを生成する。

## 使い方
使い方は以下。

```
def list = MapList.create {
	key   | value
  //------+------
	test  | 1
	test2 | 2
}

assert list == [
	[key:"test" , value:1],
	[key:"test2", value:2],
	]
```

クロージャの最初の行がヘッダ。`|`で区切られた各要素が各列の列名を定義する。  
２行目以降は、行のデータ。`|`で区切った各要素が対応する列の値になる。

