package org.start2do

import org.ansj.splitWord.Analysis
import org.apache.lucene.analysis.Analyzer
import org.ansj.library.AmbiguityLibrary

import org.ansj.library.SynonymsLibrary

import org.ansj.library.DicLibrary

import org.ansj.library.StopLibrary
import org.ansj.lucene6.AnsjAnalyzer
import org.apache.lucene.document.*
import org.apache.lucene.index.*
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.TermQuery
import org.apache.lucene.store.Directory

import java.nio.file.Paths

import org.apache.lucene.store.FSDirectory
import java.util.*


class Demo1 {
    companion object {
        @JvmStatic
        fun main(arg: Array<String>?) {
            val args: MutableMap<String, String> = HashMap()
            args["type"] = AnsjAnalyzer.TYPE.nlp_ansj.name
            args[StopLibrary.DEFAULT] = "停用词典KEY"
            args[DicLibrary.DEFAULT] = "自定义词典KEY"
            args[SynonymsLibrary.DEFAULT] = "同义词典KEY"
            args[AmbiguityLibrary.DEFAULT] = "歧义词典KEY"
            args["isNameRecognition"] = "是否开启人名识别, 默认true"
            args["isNumRecognition"] = "是否开启数字识别, 默认true"
            args["isQuantifierRecognition"] = "是否开启量词识别, 默认true"
            args["isRealName"] = "是否保留原字符, 默认false"
            val analyzer: Analyzer = AnsjAnalyzer(args)
            val indexWriterConfig = IndexWriterConfig(analyzer)
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND)
            val path = "/Volumes/RamDisk/Index"
            val dir: Directory = FSDirectory.open(Paths.get(path))
            val writer = IndexWriter(dir, indexWriterConfig)
            val document = Document()
            //string不会分词
//            document.add(StringField("name", "你好时间", Field.Store.YES))
//            document.add(TextField("name", "Stop,时间", Field.Store.YES))
//            document.add(TextField("name", "你好时间", Field.Store.YES))
//            document.add(
//                StringField(
//                    "date",
//                    DateTools.dateToString(Date(), DateTools.Resolution.SECOND),
//                    Field.Store.YES
//                )
//            )
//            writer.addDocument(document)
//            writer.close()
            val reader = DirectoryReader.open(FSDirectory.open(Paths.get(path)))
//            val searcher = IndexSearcher(reader)
            val searcher = MultiFieldQueryParser(arrayOf("name"), analyzer, mutableMapOf("date", 0.5f))
            val parser = QueryParser("name", analyzer)

            //搜索时间
//            for (doc in searcher.search(parser.parse("时间"), 100).scoreDocs) {
            //相关性
//            for (doc in searcher.parse(TermQuery(Term("name", "时间")), 100).scoreDocs) {
//                println("DocId:${doc.doc}")
//                println("Doc评分:${doc.score}")
//                println("ShareIndex:${doc.shardIndex}")
//                println(searcher.doc(doc.doc))
//            }

        }
    }
}