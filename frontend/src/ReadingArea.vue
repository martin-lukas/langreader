<template>
  <div id="reading-area">
    <p v-for="(paragraph, index) in paragraphs" :key="index">
      <span :class="(string.isWord) ? 'word' : 'other'"
            v-for="(string, index) in paragraph"
            :key="index">
        <a href="#"
           :class="string.str.type"
           v-if="string.isWord"
           @click.prevent
           @keyup.37="focusPrevious"
           @keyup.39="focusNext"
           @keyup.65="(e) => {$emit('update-word-type', string.str, 'KNOWN'); focusNext(e)}"
           @keyup.83="(e) => {$emit('update-word-type', string.str, 'STUDIED'); focusNext(e)}"
           @keyup.68="(e) => {$emit('update-word-type', string.str, 'IGNORED'); focusNext(e)}"
           @keyup.82="$emit('reset-word', string.str)">{{ string.str.word }}</a>
        <template v-else>{{ string.str.word }}</template>
      </span>
    </p>
  </div>
</template>

<script>
  import {getClassName} from "./utils";

  export default {
    props: {
      strings: Array,
      stringCounts: Array,
      select: Function
    },
    data() {
      return {
        paragraphs: []
      }
    },
    methods: {
      focusPrevious(event) {
        const previousWordElement = this.getPreviousWordElement(event.target);
        if (previousWordElement) {
          previousWordElement.focus({preventScroll: true});
        }
      },
      focusNext(event) {
        // DOM didn't update quickly enough so it might've jumped to a previously new word,
        // now known, but not updated in the DOM yet
        this.$nextTick(() => {
          const nextWordElement = this.getNextWordElement(event.target)
          if (nextWordElement) {
            nextWordElement.focus({preventScroll: true});
          }
        });
      },
      getPreviousWordElement(wordElement) {
        let previousWordElement = null;
        let previousParent = wordElement.parentNode.previousSibling;
        // look for closest new word
        // first looking through current paragraph
        while (previousParent) {
          if (getClassName(previousParent) !== 'other'
            && getClassName(previousParent.firstChild) === '') {

            previousWordElement = previousParent.firstChild;
            break;
          }
          previousParent = previousParent.previousSibling;
        }
        // now going through everything before current word, even in prev. paragraphs
        if (previousWordElement === null) {
          let previousParagraph = wordElement.parentNode.parentNode.previousSibling;
          // going paragraph by paragraph
          parLoop: while (previousParagraph) {
            let previousChild = previousParagraph.lastChild;
            // going through elements in each paragraph
            while (previousChild) {
              if (getClassName(previousChild) !== 'other'
                && getClassName(previousChild.firstChild) === '') {

                previousWordElement = previousChild.firstChild;
                break parLoop;
              }
              previousChild = previousChild.previousSibling;
            }
            previousParagraph = previousParagraph.previousSibling;
          }
        }

        return previousWordElement;
      },
      getNextWordElement(wordElement) {
        let nextWordElement = null;
        let nextParent = wordElement.parentNode.nextSibling;
        // look for closest new word
        // first looking through current paragraph
        while (nextParent) {
          if (getClassName(nextParent) !== 'other' && getClassName(nextParent.firstChild) === '') {
            nextWordElement = nextParent.firstChild;
            break;
          }
          nextParent = nextParent.nextSibling;
        }
        // now going through rest of the document
        if (nextWordElement === null) {
          let nextParagraph = wordElement.parentNode.parentNode.nextSibling;
          // going paragraph by paragraph
          parLoop: while (nextParagraph) {
            let nextChild = nextParagraph.firstChild;
            // going through elements in each paragraph
            while (nextChild) {
              if (getClassName(nextChild) !== 'other' && getClassName(nextChild.firstChild) === '') {
                nextWordElement = nextChild.firstChild;
                break parLoop;
              }
              nextChild = nextChild.nextSibling;
            }
            nextParagraph = nextParagraph.nextSibling;
          }
        }

        return nextWordElement;
      },
      focusOnFirst() {
        const firstPar = document.getElementById('reading-area').firstChild;
        let firstColoredWord = null;
        if (firstPar) {
          let firstWord = firstPar.firstChild;
          if (getClassName(firstWord) !== 'other' && getClassName(firstWord.firstChild) === '') {
            firstColoredWord = firstWord.firstChild;
          } else {
            firstColoredWord = this.getNextWordElement(firstWord.firstChild);
          }
          if (firstColoredWord) {
            firstColoredWord.focus({preventScroll: true});
          }
        }
      }
    },
    created() {
      console.time("createParagraphs");

      const strings = [...this.strings];
      let paragraphsOfStrings = [];
      for (let i = 0; i < this.stringCounts.length; i++) {
        let paragraphOfStrings = [];
        for (let j = 0; j < this.stringCounts[i]; j++) {
          paragraphOfStrings.push(strings.shift());
        }
        paragraphsOfStrings.push(paragraphOfStrings);
      }
      this.paragraphs = paragraphsOfStrings;

      console.timeEnd("createParagraphs");
    },
    mounted() {
      this.focusOnFirst();
    }
  }
</script>

<style>
  #reading-area {
    font-family: arial, sans-serif;
    user-select: none;
    width: 700px;
  }

  .other {
  }
  
  a {
    display: inline-block;
    border: 1px solid white;
    border-radius: 5px;
    padding: 1px 0;
    background-color: #cfe6ff;
    text-decoration: none;
    color: black;
    cursor: pointer;
  }

  a:focus {
    outline: 0;
    color: #e00202;
  }

  .KNOWN {
    background-color: white;
  }

  .STUDIED {
    background-color: yellow;
  }

  .IGNORED {
    background-color: white;
  }
</style>
