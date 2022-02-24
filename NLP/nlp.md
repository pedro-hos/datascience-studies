NLP with OpenNLP
===

Site: https://opennlp.apache.org
Models: http://opennlp.sourceforge.net/models-1.5/

## General

It supports the most common NLP tasks: 

* Language detector: A model can be trained with Maxent, Perceptron or Naive Bayes algorithms. By default normalizes a text and the context generator extracts n-grams of size 1, 2 and 3. The n-gram sizes, the normalization and the context generator can be customized by extending the LanguageDetectorFactory.

**Sentence Detection Tool**

```
opennlp LanguageDetector langdetect-183.bin < test.txt
```

* Tokenization: Tokenization is a process of breaking down the given sentence into smaller pieces like words, punctuation marks, numbers etc.

    _Tokenization is usually a preprocess for others "tasks"_

    OpenNLP offers multiple tokenizer implementations:

    * Whitespace Tokenizer - A whitespace tokenizer, non whitespace sequences are identified as tokens
    
    * Simple Tokenizer - A character class tokenizer, sequences of the same character class are tokens

    * Learnable Tokenizer - A maximum entropy tokenizer, detects token boundaries based on probability model

    **Tokenizer Tools**

    ```
    opennlp TokenizerME ../models/opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin < token-en.txt > tokenized.txt
    ```

    **Detokenizing**

    Detokenizing is simple the opposite of tokenization, the original non-tokenized string should be constructed out of a token sequence.

    You can take a look at the [PT Dictionary](https://github.com/apache/opennlp/blob/master/opennlp-tools/lang/pt/tokenizer/pt-detokenizer.xml)

* Sentence segmentation/detection: The OpenNLP Sentence Detector can detect that a punctuation character marks the end of a sentence or not. Usually Sentence Detection is done before the text is tokenized and that's the way the pre-trained models on the web site are trained, but it is also possible to perform tokenization first and let the Sentence Detector process the already tokenized text.

    **Sentence Detection Tool**

    ```
    opennlp SentenceDetector en-sent.bin < input.txt >  output.txt
    ```

    **Sentence Detector Training**

    ```
    opennlp SentenceDetectorTrainer -model ../models/pt-sent.bin -lang pt -data pt-sent.train -encoding UTF-8
    ```

* Part-of-speech tagging:
* Named entity extraction:

    The Name Finder can detect named entities, locations, numbers etc in text. To be able to detect entities the Name Finder needs a model. The model is dependent on the language and entity type it was trained for.

    **Name Finder Tool**

    ```
    opennlp TokenNameFinder ../models/en-ner-person.bin < name-finder.input
    ```
    **Name Finder Training**

    ```
    opennlp TokenNameFinderTrainer -model models/pt-ner-text-person.bin -lang pt -data test/pt-name-finder-text.input -encoding UTF-8
    ```

* Classifying: The OpenNLP Document Categorizer can classify text into pre-defined categories. It is based on maximum entropy framework. 

    **Classifying/Document Categorizer Training**

    ```
    opennlp DoccatTrainer -model models/ge-soccer-clubs-doccat.bin -lang pt -data test/ge_soccer_clubs_doccat.train -encoding UTF-8
    ```

* Chunking:
* Parsing:
* Coreference resolution:

OpenNLP also includes:

* maximum entropy 
* Perceptron based machine learning.

## Usage

You can use OpenNLP by [Java Coding](https://opennlp.apache.org/docs/1.9.4/manual/opennlp.html#intro.api) or [CLI Tool](https://opennlp.apache.org/docs/1.9.4/manual/opennlp.html#intro.cli) 

**References**

_General_
1. https://github.com/apache/opennlp/tree/master/opennlp-tools/src/test/resources/opennlp/tools

_Tokenization_

1. https://www.analyticsvidhya.com/blog/2020/05/what-is-tokenization-nlp/
2. https://neptune.ai/blog/tokenization-in-nlp

_Tutorials_

1. https://www.tutorialkart.com/opennlp/apache-opennlp-tutorial/
2. https://itsallbinary.com/natural-language-processing-in-java-using-apache-opennlp-document-categorizer-simple-example-for-beginners/
