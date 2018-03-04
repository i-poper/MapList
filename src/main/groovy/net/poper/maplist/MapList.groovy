package net.poper.maplist;

class MapList {
    static private class Row {
        /** １行分の情報を保持する List */
        def row
        /**
         * row にカラムの値を追加する
         * @param b リストに追加する値
         * @return b を追加したリストを保持する Row
         */
        def or( b ) {
            row.add( b )
            this
        }
    }

    /**
     * １行が配列になっている情報を配列で持つ。
     * static で宣言しないと使用できないので、ThreadLocal な変数として作成する
     */
    static ThreadLocal<List> context = new ThreadLocal<List>()

    // 最初の '|' の前後の場合は結果を Row で返す
    static or( a, b ) {
        addRow( a, b )
    }

    // 数値の場合は'|'の演算が行えるので Row を返すように定義が必要
    static or( Integer a, Integer b ) {
        addRow( a, b )
    }
    
    // 真偽地の場合は'|'の演算が行えるので Row を返すように定義が必要
    static or( Boolean a, Boolean b ) {
        addRow( a, b )
    }

    /** 
     * [ a, b ] となるリストを保持する Row を作成
     * ThrealLocal の List に追加する
     *
     * @param a リストに追加する値
     * @param b リストに追加する値
     * @return [a,b]を保持する Row
     */
    static addRow( a, b ) {
        Row row = new Row( row: [a])
        context.get().add( row )
        row.or(b)
    }

    /**
     * Spock形式のクロージャから１行がマップになっているリストを作成して返す
     * 例)
     * <pre>
     * assert MapList.create {
     *   a | b | c
     *   1 | 2 | 3
     *   x | y | z
     * } == [ [a:1, b:2, c:3], [a:x, b:y, c:z] ]
     * </pre>
     *
     * @param c  クロージャ
     * @return １行がマップになっているリスト
     */
    static create( Closure c ) {
        context.set([]) // リストを初期化
        use(MapList) {
            c.delegate = new MapList( )
            c.resolveStrategy = Closure.DELEGATE_FIRST
            c()
        }

        def table = context.get()
        def header = table.head() // 先頭はヘッダ
        table.tail().collect { row ->
            [ header.row, row.row ].transpose().collectEntries { [(it[0]):(it[1])] }
        }
    }

    // クオートしなくても文字列として扱えるようにする
    def getProperty(String property) {
        property
    }
}

