# Socket daemon protocol

The daemon has been designed to serve a PHP server, most of the time it will not return with multiple objects...   

## All data uses [BIG_ENDIAN](https://en.wikipedia.org/wiki/Endianness)
 **When referring as Emotecraft or EmoteX binary, I mean [KosmX/emotes/wiki/Emote-binary](https://github.com/KosmX/emotes/wiki/Emote-binary)**  
 **Ints are 32-bit signed integers, Strings are binaries, every binary starts with a starting size-indicator integer, there may be zero bytes in the binary.**  

### Request
 - `int` binary data length
 - `byte` input count
     - `byte` input #i binary type
     - `binary` input #i binary data
 - `byte` output count, often one  
   - `byte` requested output #o type

### Response
- `int` binary data length
- `byte` output count, should be the same as the requested or 0 if received invalid binary
  - `byte` output type, should be the same order as requested
  - `binary` #o binary

The system's response for invalid requests is undefined, for valid requests but invalid binaries, the return count is 0 (or negative signed 8-bit integer)

### Types
- 0 nothing
- 1 emotecraft binary
- 2 some json
- 3 png icon
- 4 opennbs sound (not yet actually supported)
- 5 quark emote
- 8 emote header json

The emote data (binary or json) will override everything, send those **first**.  
The header will only __set__ the existing emote.  