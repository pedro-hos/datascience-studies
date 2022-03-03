NLP with OpenNLP
===

"Natural Language Processing, or NLP for short, is broadly defined as the automatic manipulation of natural language, like speech and text, by software." [What Is Natural Language Processing?
](https://machinelearningmastery.com/natural-language-processing/)

- Site: https://opennlp.apache.org
- Models: http://opennlp.sourceforge.net/models-1.5/

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

* Part-of-speech tagging: The Part of Speech Tagger marks tokens with their corresponding word type based on the token itself and the context of the token. A token might have multiple pos tags depending on the token and the context. The OpenNLP POS Tagger uses a probability model to predict the correct pos tag out of the tag set. To limit the possible tags for a token a tag dictionary can be used which increases the tagging and runtime performance of the tagger.

    **Part-of-speech tag Tool**

    ```{bash}
    opennlp POSTagger models/pt-pos-perceptron.bin < test/sentense-pt.txt
    ```

    * [Portugese Tags Dictionary](https://www.sketchengine.eu/portuguese-tagset/)
    
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

* Lemmatizer: The lemmatizer returns, for a given word form (token) and Part of Speech tag, the dictionary form of a word, which is usually referred to as its lemma. A token could ambiguously be derived from several basic forms or dictionary words which is why the postag of the word is required to find the lemma. For example, the form `show' may refer to either the verb "to show" or to the noun "show". Currently OpenNLP implement statistical and dictionary-based lemmatizers.

    **Lemmatizer Tool**
    
    ```
    $ opennlp LemmatizerME en-lemmatizer.bin < sentences
    ```

   **Lemmatizer Training**

    ```
    $ opennlp LemmatizerTrainerME -model en-lemmatizer.bin -params PerceptronTrainerParams.txt -lang en -data en-lemmatizer.train -encoding UTF-8

    ```

* Chunker: Text chunking consists of dividing a text in syntactically correlated parts of words, like noun groups, verb groups, but does not specify their internal structure, nor their role in the main sentence.


* Parser: A parser returns a parse tree from a sentence according to a phrase structure grammar.

Other concepts:

* Maximum entropy: “[Maximum entropy classification] is a classification method that generalizes logistic regression to multiclass problems, i.e. with more than two possible discrete outcomes.” 

    The maximum entropy model is a conditional probability model p(y|x) that allows us to predict class labels given a set of features for a given data point. It does the inference by taking trained weights and performs linear combinations to find the tag with the highest probability by finding the highest score for each tag.

* Perceptron: 

* Naive Bayes: A Naive Bayes classifier is a simple probabilistic classifier based on applying Bayes' theorem (from Bayesian statistics) with strong (naive) independence assumptions. A more descriptive term for the underlying probability model would be "independent feature model".

* N-Gram: 

* Cutoff: 

    Example classification:

    ```
    positive	 I love this. I like this. I really love this product. We like this.

    negative	 I hate this. I dislike this. We absolutely hate this. I really hate this product.
    ```
    Cut off value is used to avoid words as feature whose counts are less than cut off. If cut off was more than 2, then word “love” might not be considered as feature & we might get wrong results.  Generally cut off value is useful to avoid creating unnecessary features for words which rarely occur. In this example word “I” appears 6 times, so if you change cut off to 7, not a single word qualifies to be a feature so you will get `Exception in thread "main" opennlp.tools.util.InsufficientTrainingDataException: Insufficient training data to create model.`

## Usage

You can use OpenNLP by [Java Coding](https://opennlp.apache.org/docs/1.9.4/manual/opennlp.html#intro.api) or [CLI Tool](https://opennlp.apache.org/docs/1.9.4/manual/opennlp.html#intro.cli) 

**References**

_General_

1. https://github.com/apache/opennlp/tree/master/opennlp-tools/src/test/resources/opennlp/tools

_Naive Bayes_

1. https://www.ic.unicamp.br/~rocha/teaching/2011s2/mc906/aulas/naive-bayes-classifier.pdf

_Tokenization_

1. https://www.analyticsvidhya.com/blog/2020/05/what-is-tokenization-nlp/

2. https://neptune.ai/blog/tokenization-in-nlp

_Perceptron_

1. https://machinelearningmastery.com/perceptron-algorithm-for-classification-in-python/

_NGram_

1. https://towardsdatascience.com/understanding-word-n-grams-and-n-gram-probability-in-natural-language-processing-9d9eef0fa058

_MaxEnt_

1. https://nadesnotes.wordpress.com/2016/09/05/natural-language-processing-nlp-fundamentals-maximum-entropy-maxent/

_Tutorials_

1. https://www.tutorialkart.com/opennlp/apache-opennlp-tutorial/

2. https://itsallbinary.com/natural-language-processing-in-java-using-apache-opennlp-document-categorizer-simple-example-for-beginners/

3. https://itsallbinary.com/create-your-own-chat-bot-in-java-using-apache-opennlp-artificial-intelligence-natural-language-processing/

4. https://nadesnotes.wordpress.com/2016/04/03/nlp-fundamentals/
