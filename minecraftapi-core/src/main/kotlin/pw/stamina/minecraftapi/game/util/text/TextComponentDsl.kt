/*
 * MIT License
 *
 * Copyright (c) 2018 Stamina Development
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package pw.stamina.minecraftapi.game.util.text

import pw.stamina.minecraftapi.game.client.Minecraft

@DslMarker
annotation class TextComponentDslMarker

@TextComponentDslMarker
fun textComponent(init: (TextComponentBuilder.() -> Unit)): TextComponent =
        TextComponentBuilder().apply(init).build()

operator fun String.not() = TextComponent.newText(this)

fun main(args: Array<String>) {
    val prefix = textComponent {
        style { color = TextFormatting.gray }

        prepend(!"[")
        append(!"]: ")

        text("Stamina") {
            style {
                color = TextFormatting.gold
            }
        }
    }

    val featuresEnableText = textComponent {
        text("Features: ")

        val featureIterator = listOf<String>().iterator()

        while (featureIterator.hasNext()) {
            val feature = featureIterator.next()

            text(feature) {
                style {
                    color = when (feature.isNotBlank()) {
                        true -> TextFormatting.green
                        false -> TextFormatting.red
                    }
                }
            }

            if (featureIterator.hasNext()) {
                text(", ")
            }
        }

        text(".")
    }

    Minecraft.minecraft.printChatMessage(textComponent {
        +prefix
        +featuresEnableText
    })
}